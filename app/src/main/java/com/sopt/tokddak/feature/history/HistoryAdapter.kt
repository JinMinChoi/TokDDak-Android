package com.sopt.tokddak.feature.history


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDecimalFormat

class HistoryAdapter(private val context: Context) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

    var data = listOf<HistoryServerInItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_history,parent,false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class HistoryViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val txt_category : TextView = view.findViewById(R.id.txt_category)
        val txt_detail : TextView = view.findViewById(R.id.txt_detail)
        val txt_price : TextView = view.findViewById(R.id.txt_price)

        fun bind(historyItem: HistoryServerInItem){

            when(historyItem.category.toString()){
                "1" -> txt_category.text = "숙박"
                "2" -> txt_category.text = "식사"
                "3" -> txt_category.text = "간식/주류"
                "4" -> txt_category.text = "교통"
                "5" -> txt_category.text = "쇼핑"
                "6" -> txt_category.text = "액티비티"
            }
            txt_detail.text = historyItem.detail
            txt_price.text = historyItem.price.toDecimalFormat()
        }
    }
}