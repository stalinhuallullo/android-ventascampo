package dev.lstr.llevateclaro.presentation.presenter.view_model

import android.arch.lifecycle.*
import android.content.Context
import dev.lstr.llevateclaro.data.datasource.DataSourceCallback
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.data.model.DetalleReferidoE
import dev.lstr.llevateclaro.data.model.DetalleReferidoObsE
import dev.lstr.llevateclaro.data.repository.BusinessRepository
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.indeterminateProgressDialog

/**
 * Created by lesternr on 5/20/18.
 */
class DetalleReferidoViewModel : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()

    private val userSessionDataSource by lazy{
        BusinessRepository.provideBusiness()
    }

    var messageLiveData = MutableLiveData<String>()
    var detalleReferidoLiveData = MutableLiveData<DetalleReferidoE>()
    var detalleReferidoObsLiveData = MutableLiveData<DetalleReferidoObsE>()

    private var context: Context? = null

    fun initViewModel(context: Context){
        this.context = context
    }

    fun getDetalleReferido(ref_id:String, action: String){
        val user_id = CurrentUser.getInstance(context!!).getUser()!!.id

        val loadingView = context!!.indeterminateProgressDialog("Cargando datos...")
        loadingView.show()

        userSessionDataSource.detalleReferido(user_id, ref_id, action, object: DataSourceCallback {
            override fun onSuccess(data: Object) {
                loadingView.dismiss()

                val dataItem = data as DetalleReferidoE
                detalleReferidoLiveData.value = dataItem
            }
            override fun onError(error: String) {
                loadingView.dismiss()

                messageLiveData.value = error
            }
        })
    }

    fun getDetalleReferidoObs(ref_id:String, action: String){

        val loadingView = context!!.indeterminateProgressDialog("Cargando datos...")
        loadingView.show()

        userSessionDataSource.detalleReferidoObs(ref_id, action, object: DataSourceCallback {
            override fun onSuccess(data: Object) {
                loadingView.dismiss()

                val dataItem = data as DetalleReferidoObsE
                detalleReferidoObsLiveData.value = dataItem
            }
            override fun onError(error: String) {
                loadingView.dismiss()

                //messageLiveData.value = error
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