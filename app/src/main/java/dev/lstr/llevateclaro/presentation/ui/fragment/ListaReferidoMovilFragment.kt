package dev.lstr.llevateclaro.presentation.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.lstr.llevateclaro.BuildConfig
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.data.model.ReferidoE
import dev.lstr.llevateclaro.presentation.presenter.view_model.ListaReferidoViewModel
import dev.lstr.llevateclaro.presentation.ui.activity.DetalleReferidoActivity
import dev.lstr.llevateclaro.presentation.ui.adapter.ReferidosAdapter
import kotlinx.android.synthetic.main.fragment_lista_referido.*

/**
 * Created by lesternr on 5/5/18.
 */
class ListaReferidoMovilFragment : BaseFragment() {
    var referidosAdapter: ReferidosAdapter? = null
    var referidosList = ArrayList<ReferidoE>()

    private lateinit var listaReferidoViewModel: ListaReferidoViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_lista_referido, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){
        rv_referidos.layoutManager = LinearLayoutManager(context)
        rv_referidos.setHasFixedSize(true)
        referidosAdapter = ReferidosAdapter(referidosList, object : ReferidosAdapter.Callback {
            override fun onItemSelected(pos: Int) {
                Log.d("POS ", pos.toString())
                gotoReferidoDetalle(referidosList.get(pos))
            }
        })
        rv_referidos.adapter = referidosAdapter



        listaReferidoViewModel = ViewModelProviders.of(this).get(ListaReferidoViewModel::class.java)
        listaReferidoViewModel.initViewModel(context)
        listaReferidoViewModel.apply {
            messageLiveData.observe(this@ListaReferidoMovilFragment, Observer { message -> showErrorMessage(message!!) })
            listaReferidoLiveData.observe(this@ListaReferidoMovilFragment, Observer { data -> showData(data!!) })
        }

        loadData()

    }


    fun showErrorMessage(message: String) {
        txt_rv_referido.visibility = View.VISIBLE
        rv_referidos.visibility = View.GONE

        txt_rv_referido.text = message
    }

    fun loadData(){
        listaReferidoViewModel.getListaReferido("lrm")
    }

    private fun gotoReferidoDetalle(item: ReferidoE) {
        Log.d("aaaaaa", "================================")
        Log.d("item.id", item.id)

        val it = Intent(context, DetalleReferidoActivity::class.java)
        //it.putExtra("id", item.id_referido)

        it.putExtra("id", item.id)
        //it.putExtra("name", "${item.nombres} ${item.apellido_paterno} ${item.apellido_materno}")
        it.putExtra("name", "${item.last_name} ${item.name}")
        it.putExtra("action", DetalleReferidoActivity.action_detalle_referido_movil)
        startActivity(it)
    }

    fun showData(data: ArrayList<ReferidoE>) {
        txt_rv_referido.visibility = View.GONE
        rv_referidos.visibility = View.VISIBLE

        referidosList.addAll(data)
        referidosAdapter!!.notifyDataSetChanged()
    }
}