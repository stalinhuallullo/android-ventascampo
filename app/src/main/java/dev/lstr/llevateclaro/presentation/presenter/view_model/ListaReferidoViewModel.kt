package dev.lstr.llevateclaro.presentation.presenter.view_model

import android.arch.lifecycle.*
import android.content.Context
import dev.lstr.llevateclaro.data.datasource.DataSourceCallback
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.data.model.ReferidoE
import dev.lstr.llevateclaro.data.repository.BusinessRepository
import io.reactivex.disposables.CompositeDisposable
import java.util.*

/**
 * Created by lesternr on 5/20/18.
 */
class ListaReferidoViewModel : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()

    private val userSessionDataSource by lazy{
        BusinessRepository.provideBusiness()
    }

    var messageLiveData = MutableLiveData<String>()
    var listaReferidoLiveData = MutableLiveData<ArrayList<ReferidoE>>()

    private var context: Context? = null

    fun initViewModel(context: Context){
        this.context = context
    }

    fun getListaReferido(tipo_action: String){
        val user_id = CurrentUser.getInstance(context!!).getUser()!!.id

        userSessionDataSource.listaReferido(user_id, tipo_action, object: DataSourceCallback {
            override fun onSuccess(data: Object) {
                val dataList = data as ArrayList<ReferidoE>
                listaReferidoLiveData.value = dataList
            }
            override fun onError(error: String) {
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