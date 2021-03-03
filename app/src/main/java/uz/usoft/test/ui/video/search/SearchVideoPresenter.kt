package uz.usoft.test.ui.video.search

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.usoft.test.core.Resource
import uz.usoft.test.data.ApiService

class SearchVideoPresenter(private val apiService: ApiService) {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var view: SearchVideoView

    fun init(view: SearchVideoView) {
        this.view = view
    }

    fun getVideos() {
        view.render(Resource.loading())
        compositeDisposable.add(
            apiService.searchVideo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.render(Resource.success(it.categories[0].videos))
                    },
                    {
                        view.render(Resource.error(it.localizedMessage))
                    }
                )
        )

    }
}