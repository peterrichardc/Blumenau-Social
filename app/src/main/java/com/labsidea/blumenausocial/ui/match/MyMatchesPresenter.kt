package com.labsidea.blumenausocial.ui.match

import android.content.Context
import com.labsidea.blumenausocial.models.*
import com.labsidea.blumenausocial.ui.institution.detail.DonationsAdapter
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm

class MyMatchesPresenter: MyMatchesContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: MyMatchesContract.View
    private lateinit var institutions: MutableList<Organization>

    private var listIDNeighborhoodsSelected : ArrayList<Int?> = arrayListOf()
    private var listCausesSelected          : ArrayList<String?> = arrayListOf()
    private var listDaysOfWeekSelected      : ArrayList<String?> = arrayListOf()
    private var listTimesOfDaySelected      : ArrayList<String?> = arrayListOf()

    override fun subscribe() {}

    override fun unsubscribe() = subscriptions.clear()

    override fun attach(view: MyMatchesContract.View) {
        this.view = view
    }

    override fun makeMatchInstitutions(context: Context){
        val realm = Realm.getDefaultInstance()
        val subscribe = realm
                //Search the current Match filters
                .where(MatchFilters::class.java)
                .findAllAsync()
                .asFlowable()
                .filter { isLoaded -> isLoaded.isLoaded }
                .subscribe { filtersRealm ->
                    //Filter institutions with your matches.
                    val filters = realm.copyFromRealm(filtersRealm)

                    //Neighborhoods Filters
                    filters.filter { it.type == FiltersType.NEIGHBORHOODS.ordinal }
                            .forEach { listIDNeighborhoodsSelected.add(it.id) }

                    //Causes Filters
                    filters.filter { it.type == FiltersType.CAUSES.ordinal }
                            .forEach { listCausesSelected.add(it.id.toString()) }

                    //Days Of Week Filters
                    filters.filter { it.type == FiltersType.DAYS_OF_WEEK.ordinal }
                            .forEach { listDaysOfWeekSelected.add(it.id.toString()) }

                    //Times of Day Filters
                    filters.filter { it.type == FiltersType.TIMES_OF_DAY.ordinal }
                            .forEach { listTimesOfDaySelected.add(it.id.toString()) }

                    if (filters.isNotEmpty()) {
                        //Preparing filters
                        val realmQuery = realm.where(Organization::class.java)
                        realmQuery
                                .findAllAsync()
                                .asFlowable()
                                .filter { isLoaded -> isLoaded.isLoaded }
                                .subscribe { institutionsTables ->
                                    val institutionsPoints = hashMapOf<Organization, Double>()
                                    institutionsTables.forEach {
                                        val institution = realm.copyFromRealm(it)
                                        institutionsPoints[institution] = getPointsInstitution(institution)
                                    }

                                    //Sort Map by points
                                    val sortedInstitutionsByPoints = institutionsPoints.toList().sortedByDescending { (_, value) -> value }.toMap()
                                    institutions = sortedInstitutionsByPoints.keys.toMutableList()
                                    val adapter = MyMatchesPagerAdapter(context, institutions) { institutionID -> view onClickInstitution institutionID }
                                    view onMatchReady adapter
                                }
                    }
                    else
                        view.onNeedMakeMatch()
                }
        subscriptions.add(subscribe)
    }

    private fun getPointsInstitution(institution: Organization): Double{
        var points = 0.0

        if (listIDNeighborhoodsSelected.contains(institution.neighborhood))
            points = 5.0

        institution.selected_causes?.split(";")?.forEach {causeID ->
            if (listCausesSelected.contains(causeID))
                points += 5
        }

        /*institution.selected_days?.split(";")?.forEach {dayID ->
            if (listDaysOfWeekSelected.contains(dayID))
                points += 0.5
        }

        institution.selected_periods?.split(";")?.forEach {periodID ->
            if (listTimesOfDaySelected.contains(periodID))
                points += 0.5
        }*/

        return points
    }

    override fun getDonationsAdapter(institutionIndex: Int) = DonationsAdapter(institutions[institutionIndex].donations)
}