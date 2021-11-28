package raui.imashev.travelapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.flight_item.view.*
import raui.imashev.travelapp.R
import raui.imashev.travelapp.pojo.Data
import raui.imashev.travelapp.pojo.Price

class FlightsAdapter :
    RecyclerView.Adapter<FlightsAdapter.ViewHolder>() {

    var datum: List<Data> = listOf()
    var onClickListener: OnClickListener? = null
    private val transfersCount = 1 //Оставил переменную для настройки количества пересадок

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.flight_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = datum[position]
        with(holder) {
            textViewPrice.text =
                getMinValue(data.prices) //Вызов метода для получения минимального значения
            textViewCurrency.text = data.currency
            textViewFrom.text = data.trips[0].from
            textViewTo.text = data.trips[data.trips.size - transfersCount].to
            textViewTransfersCount.text = (data.trips.size - transfersCount).toString()

            itemView.setOnClickListener {
                onClickListener?.onClick(data)
            }
        }

    }

    override fun getItemCount(): Int = datum.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewPrice: TextView = itemView.textViewPrice
        val textViewCurrency: TextView = itemView.textViewCurrency
        val textViewFrom: TextView = itemView.textViewFrom
        val textViewTo: TextView = itemView.textViewTo
        val textViewTransfersCount: TextView = itemView.textViewTransfersCount
    }

    interface OnClickListener {
        fun onClick(data: Data)
    }

    private fun getMinValue(prices: List<Price>): String {
        var minAmount: Int
        minAmount = Int.MAX_VALUE
        for (i in prices) {
            if (i.amount < minAmount) {
                minAmount = i.amount
            }
        }
        return minAmount.toString()
    }
}