/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.api

import com.labsidea.blumenausocial.models.OrganizationAdditionalInformationList
import com.labsidea.blumenausocial.models.OrganizationList
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import io.reactivex.schedulers.Schedulers

interface APIServiceInterface{

    @GET("institution")
    fun getInstitutionsList(): Observable<OrganizationList>

    @GET("filter")
    fun getInstitutionsAdditionalInformationList(): Observable<OrganizationAdditionalInformationList>

    companion object Factory {
        fun create(): APIServiceInterface {
            val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(rxAdapter)//RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://blumenau-social-api.herokuapp.com/")//"https://dl.dropboxusercontent.com/")
                    .build()

            return retrofit.create(APIServiceInterface::class.java)
        }
    }
}