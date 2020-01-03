package com.sopt.tokddak.feature.history


import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDecimalFormat
import kotlinx.android.synthetic.main.activity_expense_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {

    private lateinit var history_rv_outer : RecyclerView
    private lateinit var historyAdapter : HistoryAdapter
    private lateinit var divider : DividerItemDecoration

    private lateinit var txt_total_price : TextView

    private val getHistory : HistoryServer = GetHistoryServer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_history)

        history_rv_outer = findViewById(R.id.history_rv_outer)
        init(history_rv_outer)

        img_back.setOnClickListener {
            finish()
        }
    }

    private fun init(view : RecyclerView){

        txt_total_price = findViewById(R.id.txt_total_price)

        divider = DividerItemDecoration(this, 1)
        divider.setDrawable(resources.getDrawable(R.drawable.rv_divider))

        historyAdapter = HistoryAdapter(this)

        view.adapter = historyAdapter
        view.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        view.addItemDecoration(divider)

        getHistory.getHistory(1).enqueue(object : Callback<HistoryServerItem>{
            override fun onFailure(call: Call<HistoryServerItem>, t: Throwable) {
                Log.e("히스토리 서버 오류","t = $t")
            }

            override fun onResponse(
                call: Call<HistoryServerItem>,
                response: Response<HistoryServerItem>
            ) {
                if(response.isSuccessful){
                    val history = response.body()!!
                    val historyIn = response.body()!!.data

                    historyAdapter.data = historyIn
                    historyAdapter.notifyDataSetChanged()
                    var total = 0
                    historyIn.forEach { total += it.price }
                    txt_total_price.text = total.toDecimalFormat()

                }
            }
        })


    }
}