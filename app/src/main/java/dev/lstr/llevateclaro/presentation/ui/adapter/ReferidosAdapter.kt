package dev.lstr.llevateclaro.presentation.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
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
    var name_progress: String = ""
    var status_progress: String = ""
    var color: String = ""
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.lv_referidos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        //Log.d("TITLE", "=================  ITEMS  ============")
        //Log.d("position", " =>  " + position.toString())
        val obj = data.get(position)
        //val referal = "${obj.apellido_paterno} ${obj.apellido_materno} ${obj.nombres}  "
        val referal = "${obj.last_name} ${obj.name}"
        holder!!.txt_nombre.setText(referal)
        holder.txt_type_document.setText(obj.name_document)
        holder.txt_number_document.setText(obj.number_document)
        holder.itemView.setOnClickListener { callback.onItemSelected(position) }

        /*
        Log.d("Name =========> ", obj.last_name + obj.name)
        Log.d("number_document ==> ", obj.number_document)

        Log.d("Status =========> ", obj.status)
        Log.d("status_data =========> ", obj.status_data)
        Log.d("status_evaluation => ", obj.status_evaluation)
        Log.d("status_sale =========> ", obj.status_sale)
        Log.d("status_delivery => ", obj.status_delivery)
        */


        var data = obj.status_data.toInt()
        var evaluation = obj.status_evaluation.toInt()
        var sale = obj.status_sale.toInt()
        var delivery = obj.status_delivery.toInt()






        if (data == 1) {

            if (evaluation == 1) {

                if (sale == 1) {

                    if (delivery == 1) {

                        holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.colorAprobado))
                        name_progress = "Entregado"
                        status_progress = "Aprobado"
                        color = "#008000"

                    } else if (delivery == 2) {
                        holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.colorRechazado))
                        name_progress = "Entregado"
                        status_progress = "Rechazado"
                        color = "#ff0000"
                    } else {
                        holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.colorEspera))
                        name_progress = "Entregado"
                        status_progress = "En espera"
                        color = "#ffa500"
                    }

                } else if (sale == 2) {
                    holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.colorRechazado))
                    name_progress = "Venta"
                    status_progress = "Rechazado"
                    color = "#ff0000"
                } else {
                    holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.colorEspera))
                    name_progress = "Venta"
                    status_progress = "En espera"
                    color = "#ffa500"
                }

            } else if (evaluation == 2) {
                holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.colorRechazado))
                name_progress = "Evaluacion"
                status_progress = "Rechazado"
                color = "#ff0000"
            } else {
                holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.colorEspera))
                name_progress = "Evaluacion"
                status_progress = "En espera"
                color = "#ffa500"
            }

        } else if (data == 2) {
            holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.colorRechazado))
            name_progress = "Datos recibidos"
            status_progress = "Rechazado"
            color = "#ff0000"
        } else {
            holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.colorEspera))
            name_progress = "Datos recibidos"
            status_progress = "En espera"
            color = "#ffa500"
        }

        holder!!.txt_name_progress.setText(name_progress + " : ")
        holder!!.txt_name_progress.setTextColor(Color.parseColor(color))
        holder!!.txt_progress.setText(status_progress)
        holder!!.txt_progress.setTextColor(Color.parseColor(color))
/*
            when (data) {
                "0" -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange))
                "1" -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.green))
                "2" -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.red))
                else -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            }

            when (evaluation) {
                "0" -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange))
                "1" -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.green))
                "2" -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.red))
                else -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            }

            when (sale) {
                "0" -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange))
                "1" -> {
                    holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.green))
                    holder.txt_monto.visibility = View.VISIBLE
                }
                "2" -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.red))
                else -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            }

            when (delivery) {
                "0" -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange))
                "1" -> {
                    holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.green))
                    holder.txt_monto.visibility = View.VISIBLE
                }
                "2" -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.red))
                else -> holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange))
            }

            //holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.green))

        } else {
            holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange_light))
        }
*/


        /*if(obj.estado != null){
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
        }*/

        //holder.cv_main.cardBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.context, R.color.orange_light))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val txt_nombre: TextView
        val txt_type_document: TextView
        val txt_number_document: TextView
        val cv_main: CardView
        var txt_name_progress: TextView
        var txt_progress: TextView
        init {
            txt_nombre = itemView!!.findViewById(R.id.txt_nombre)
            txt_type_document = itemView!!.findViewById(R.id.txt_type_document)
            txt_number_document = itemView!!.findViewById(R.id.txt_number_document)
            cv_main = itemView!!.findViewById(R.id.cv_main)
            txt_name_progress = itemView!!.findViewById(R.id.txt_name_progress)
            txt_progress = itemView!!.findViewById(R.id.txt_progress)
        }
    }

    interface Callback{
        fun onItemSelected(pos: Int)
    }
}