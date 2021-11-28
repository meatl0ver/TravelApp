package raui.imashev.travelapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.ViewModelProvider
import raui.imashev.travelapp.R
import raui.imashev.travelapp.ViewModel
import raui.imashev.travelapp.databinding.FragmentFlightBinding
import raui.imashev.travelapp.pojo.Trip


class FlightInfoFragment : Fragment() {
    private lateinit var viewModel: ViewModel
    private lateinit var vb: FragmentFlightBinding


    private val keyID = "ID"
    private val keyPositionType = "POSITION_TYPE"
    private val defaultValue = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        vb = FragmentFlightBinding.bind(view)
        val bundle = this.arguments
        if (bundle == null) {
            mistake()
        } else {
            val id = bundle.getInt(keyID, defaultValue)
            val positionType = bundle.getInt(keyPositionType, defaultValue)
            val data = viewModel.getItemForInfoPage(id)

            vb.textViewTransfersList.text = makeTransfersText(data.trips)
            vb.textViewTypeInfo.text = data.prices[positionType].type
            vb.textViewCurrency.text = data.currency
            vb.textViewPrice.text = data.prices[positionType].amount.toString()
            vb.textViewFrom.text = data.trips[0].from
            vb.textViewTo.text = data.trips[data.trips.size - 1].to
            vb.textViewTransfersCount.text = (data.trips.size - 1).toString()
        }

    }

    private fun mistake() {
        Toast.makeText(context, R.string.mistake, LENGTH_LONG).show()
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.container, FlightsFragment(), null)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    //Создание строки с пересадками
    private fun makeTransfersText(trips: List<Trip>): String {
        var transfers = ""
        for (i in trips) {
            transfers +=
                "${trips.indexOf(i) + 1}. From: ${i.from} \n" +
                        "    To: ${i.to}\n\n"
        }
        return transfers
    }
}