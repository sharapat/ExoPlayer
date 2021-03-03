package uz.usoft.test.data

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import uz.usoft.test.data.model.MyResponse

interface ApiService {
    @GET("bikashthapa01/myvideos-android-app/master/data.json")
    fun searchVideo()
        : Observable<MyResponse>
}