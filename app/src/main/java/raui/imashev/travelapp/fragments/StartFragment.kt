package raui.imashev.travelapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import raui.imashev.travelapp.R
import raui.imashev.travelapp.ViewModel
import raui.imashev.travelapp.databinding.FragmentStartBinding


class StartFragment : Fragment() {
    private lateinit var viewModel: ViewModel
    private lateinit var vb: FragmentStartBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragmentStartBinding.bind(view)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        vb.loadDataButton.setOnClickListener {
            viewModel.loadData()
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.container, FlightsFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }
}