package raui.imashev.travelapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_flights.*
import raui.imashev.travelapp.R
import raui.imashev.travelapp.ViewModel
import raui.imashev.travelapp.adapters.FlightsAdapter
import raui.imashev.travelapp.databinding.FragmentFlightsBinding
import raui.imashev.travelapp.pojo.Data


class FlightsFragment : Fragment() {
    private lateinit var viewModel: ViewModel
    private lateinit var vb: FragmentFlightsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flights, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragmentFlightsBinding.bind(view)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.getItemsForRV()

        val adapter = FlightsAdapter()
        adapter.onClickListener = object : FlightsAdapter.OnClickListener {
            override fun onClick(data: Data) {
                val dialog = SelectClassDialogFragment(data)
                activity?.supportFragmentManager?.let {
                    dialog.show(it, null)
                }
            }
        }
        rvFlights.adapter = adapter
        adapter.datum = viewModel.getItemsForRV()
    }


}