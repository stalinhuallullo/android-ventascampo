package dev.lstr.llevateclaro.presentation.presenter.view_model

import android.arch.lifecycle.*
import android.content.Context
import dev.lstr.llevateclaro.data.datasource.DataSourceCallback
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.data.repository.UserSessionRepository
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.indeterminateProgressDialog

/**
 * Created by lesternr on 5/20/18.
 */
class RecoverPassViewModel : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()
    private val userSessionDataSource by lazy{
        UserSessionRepository.provideUserSession()
    }

    var messageLiveData = MutableLiveData<String>()
    var resultLiveData = MutableLiveData<Boolean>()

    private var context: Context? = null

    fun initViewModel(context: Context){
        this.context = context
    }

    fun sendEmail(email: String){
        val loadingView = context!!.indeterminateProgressDialog("Enviando datos...")
        loadingView.show()

        userSessionDataSource.recuperarPass(email, object: DataSourceCallback {
            override fun onSuccess(data: Object) {
                loadingView.dismiss()

                val sent = data as Boolean
                resultLiveData.value = sent
            }
            override fun onError(error: String) {
                loadingView.dismiss()

                messageLiveData.value = error
            }
        })
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