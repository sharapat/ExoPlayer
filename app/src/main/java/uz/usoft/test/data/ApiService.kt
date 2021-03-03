package uz.usoft.test.data

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import uz.usoft.test.data.model.MyResponse

interface ApiService {
    @GET("search")
    fun searchVideo(@Query("part") part: String,
                    @Query("q") searchText: String,
                    @Query("key") key: String)
        : Observable<MyResponse>
}