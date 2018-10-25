package dev.lstr.llevateclaro.presentation.presenter.view_model

import android.arch.lifecycle.*
import android.content.Context
import dev.lstr.llevateclaro.data.datasource.DataSourceCallback
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.data.model.UserE
import dev.lstr.llevateclaro.data.repository.UserSessionRepository
import dev.lstr.llevateclaro.presentation.util.TrackerGA
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.indeterminateProgressDialog

/**
 * Created by lesternr on 5/20/18.
 */
class LoginViewModel : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()
    private val userSessionDataSource by lazy{
        UserSessionRepository.provideUserSession()
    }

    var messageLiveData = MutableLiveData<String>()
    var resultLiveData = MutableLiveData<LiveResult>()

    private var context: Context? = null

    fun initViewModel(context: Context){
        this.context = context
    }

    fun login(user:String, password:String){
        val loadingView = context!!.indeterminateProgressDialog("Validando datos...")
        loadingView.show()

        userSessionDataSource.login(user, password, object: DataSourceCallback{
            override fun onSuccess(data: Object) {
                loadingView.dismiss()

                storeUser(data as UserE)
                resultLiveData.value = LiveResult(true, "")
            }
            override fun onError(error: String) {
                loadingView.dismiss()

                resultLiveData.value = LiveResult(false, error)
            }
        })
    }

    private fun storeUser(user: UserE){
        CurrentUser.getInstance(context!!).saveUser(user)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun unSubscribeViewModel() {
        for (disposable in userSessionDataSource.allCompositeDisposable) {
            compositeDisposable.addAll(disposable)
        }
        compositeDisposable.clear()
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }
}