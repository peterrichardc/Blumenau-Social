package com.labsidea.blumenausocial.ui.match.filter.steps

import android.content.Context
import com.labsidea.blumenausocial.models.*
import com.labsidea.blumenausocial.ui.institution.filter.FilterAdapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm

class StepsPresenter: StepsContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: StepsContract.View

    override fun subscribe() {}

    override fun unsubscribe() = subscriptions.clear()

    override fun attach(view: StepsContract.View) {
        this.view = view
    }

    /**
     * Select all Neighborhoods and return they by view-event.
     * @param context to attach to return FilterAdapter.
     * */
    override fun loadNeighborhoodAdapter(context: Context) {
        val realm = Realm.getDefaultInstance()
        val subscribe = realm
                .where(Neighborhood::class.java)
                .findAllAsync()
                .asFlowable()
                .filter { isLoaded -> isLoaded.isLoaded }
                .subscribe { neighborhoods ->
                    val list = mutableListOf<ItemAdapter>()
                    for (neigh in neighborhoods.toList()) {
                        list.add(
                                ItemAdapter(
                                        neigh.id,
                                        neigh.name,
                                        isPreviouslySelected(realm, neigh.id, FiltersType.NEIGHBORHOODS),
                                        neigh.image
                                           )
                                )
                    }
                    view.onLoadAdapter(FilterAdapter(context, list) { itemClick -> saveMatchFilter(itemClick, FiltersType.NEIGHBORHOODS) })
                }

        subscriptions.add(subscribe)
    }

    override fun loadDaysOfWeekAdapter(context: Context) {

       /* val realm = Realm.getDefaultInstance()
        val subscribe = Observable.just(mutableListOf<ItemAdapter>())
                .subscribe { daysOfWeek ->
                    daysOfWeek.add(ItemAdapter(1, "Segunda", isPreviouslySelected(realm, 1, FiltersType.DAYS_OF_WEEK)))
                    daysOfWeek.add(ItemAdapter(2, "Terça"  , isPreviouslySelected(realm, 2, FiltersType.DAYS_OF_WEEK)))
                    daysOfWeek.add(ItemAdapter(3, "Quarta" , isPreviouslySelected(realm, 3, FiltersType.DAYS_OF_WEEK)))
                    daysOfWeek.add(ItemAdapter(4, "Quinta" , isPreviouslySelected(realm, 4, FiltersType.DAYS_OF_WEEK)))
                    daysOfWeek.add(ItemAdapter(5, "Sexta"  , isPreviouslySelected(realm, 5, FiltersType.DAYS_OF_WEEK)))
                    daysOfWeek.add(ItemAdapter(6, "Sábado" , isPreviouslySelected(realm, 6, FiltersType.DAYS_OF_WEEK)))
                    daysOfWeek.add(ItemAdapter(7, "Domingo", isPreviouslySelected(realm, 7, FiltersType.DAYS_OF_WEEK)))

                    view.onLoadAdapter(FilterAdapter(context, daysOfWeek) { itemClick -> saveMatchFilter(itemClick, FiltersType.DAYS_OF_WEEK) })
                }

        subscriptions.add(subscribe)*/
    }

    override fun loadTimesOfDayAdapter(context: Context) {

        /*val subscribe = Observable.just(mutableListOf<ItemAdapter>())
                .subscribe { timesOfDay ->
                    timesOfDay.add(ItemAdapter(1, "Manhã"))
                    timesOfDay.add(ItemAdapter(2, "Tarde"))
                    timesOfDay.add(ItemAdapter(3,"Noite"))

                    view.onLoadAdapter(FilterAdapter(context, timesOfDay) { itemClick -> saveMatchFilter(itemClick, FiltersType.TIMES_OF_DAY) })
                }

        subscriptions.add(subscribe)*/
    }

    /**
     * Select all Causes and return they by view-event.
     * @param context to attach to return FilterAdapter.
     * */
    override fun loadCausesAdapter(context: Context) {
        val realm = Realm.getDefaultInstance()
        val subscribe = realm
                .where(Causes::class.java)
                .findAllAsync()
                .asFlowable()
                .filter { isLoaded -> isLoaded.isLoaded }
                .subscribe { causes ->
                    val list = mutableListOf<ItemAdapter>()
                    for (cause in causes.toList()) {
                        list.add(
                                ItemAdapter(
                                        cause.id,
                                        cause.name,
                                        isPreviouslySelected(realm, cause.id, FiltersType.CAUSES),
                                        cause.image
                                           )
                                )
                    }
                    view.onLoadAdapter(FilterAdapter(context, list) { itemClick -> saveMatchFilter(itemClick, FiltersType.CAUSES) })
                }

        subscriptions.add(subscribe)
    }

    private fun isPreviouslySelected(realm: Realm, id: Int, type: FiltersType): Int{
        val matchFilter =
                Realm.getDefaultInstance()
                .where(MatchFilters::class.java)
                .equalTo("id", id)
                .equalTo("type", type.ordinal)
                .findFirst()

                                                    //exists and selected
        return if (matchFilter !== null && realm.copyFromRealm(matchFilter).selected) 1 else 0
    }

    private fun Int.toBoolean(): Boolean = this == 1

    // Save in database the filter selected.
    private fun saveMatchFilter(item: ItemAdapter, type: FiltersType){
        val realm = Realm.getDefaultInstance()

        val matchFilters = MatchFilters()
        matchFilters.id = item.id
        matchFilters.type = type.ordinal
        matchFilters.selected = item.selected.toBoolean()

        realm.beginTransaction()
        realm.insertOrUpdate(matchFilters)
        realm.commitTransaction()
    }
}