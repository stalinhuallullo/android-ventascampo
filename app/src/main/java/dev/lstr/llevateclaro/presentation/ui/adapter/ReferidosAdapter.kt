package dev.lstr.llevateclaro.presentation.ui.adapter

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.lstr.llevateclaro.R
import dev.lstr.llevateclaro.data.model.ReferidoE
import org.jetbrains.anko.backgroundColor

/**
 * Created by lesternr on 5/6/18.
 */

class ReferidosAdapter(data: ArrayList<ReferidoE>, callback: Callback): RecyclerView.Adapter<ReferidosAdapter.ViewHolder>(){

    val data: ArrayList<ReferidoE> = data
    val callback = callback

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.lv_referidos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val obj = data.get(position)
        val referal = "${obj.apellido_paterno} ${obj.apellido_materno} ${obj.nombres}  "
        holder!!.txt_nombre.setText(referal)
        holder.txt_dni.setText(obj.dni)
        holder.txt_monto.setText("S/${obj.monto}")
        holder.itemView.setOnClickListener { callback.onItemSelected(position) }

        holder.txt_monto.visibility = View.GONE

        if(obj.estado != null){
            if(obj.estado == "A"){
                holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.green))
                holder.txt_monto.visibility = View.VISIBLE
            }else if(obj.estado == "E"){
                holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange_light))
            }else if(obj.estado == "R"){
                holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.red))
            }
        }
        else
        {
            holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange_light))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val txt_nombre: TextView
        val txt_dni: TextView
        val txt_monto: TextView
        val cv_main: CardView
        init {
            txt_nombre = itemView!!.findViewById(R.id.txt_nombre)
            txt_dni = itemView!!.findViewById(R.id.txt_dni)
            txt_monto = itemView!!.findViewById(R.id.txt_monto)
            cv_main = itemView!!.findViewById(R.id.cv_main)
        }
    }

    interface Callback{
        fun onItemSelected(pos: Int)
    }
}