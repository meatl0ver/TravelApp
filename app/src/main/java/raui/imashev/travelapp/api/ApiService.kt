package raui.imashev.travelapp.api

import retrofit2.http.GET
import io.reactivex.Single
import raui.imashev.travelapp.pojo.Data

interface ApiService {
    @GET("ott/search")
    fun getData(): Single<ArrayList<Data>>
}