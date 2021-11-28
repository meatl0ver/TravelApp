package raui.imashev.travelapp.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_fragment.*
import kotlinx.android.synthetic.main.dialog_fragment.view.*
import raui.imashev.travelapp.R
import raui.imashev.travelapp.pojo.Data


class SelectClassDialogFragment(data: Data) : DialogFragment() {
    private val keyPositionType = "POSITION_TYPE"
    private val keyID = "ID"
    private val marginValues = listOf(48, 0, 0, 0)

    private val trips = data.prices
    private val id = data.id

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.dialog_fragment, container, false)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(
            marginValues[0],
            marginValues[1],
            marginValues[2],
            marginValues[3]
        ) //установка левого отступа
        for ((id, i) in trips.withIndex()) {
            val button = RadioButton(context)
            button.text = "${i.type} - ${i.amount}"
            button.id = id
            button.layoutParams = params
            rootView.radioOptions.addView(button)
        }

        rootView.cancelButton.setOnClickListener { dismiss() }
        rootView.submitButton.setOnClickListener { showInfo() }
        return rootView
    }

    private fun showInfo() {
        val fragment = FlightInfoFragment()
        val bundle = Bundle()

        fragment.arguments = bundle
        val itemId = radioOptions.checkedRadioButtonId
        val selectedItem = radioOptions.getChildAt(itemId)
        if (id != null && selectedItem != null) {
            bundle.putInt(keyID, id)
            bundle.putInt(keyPositionType, selectedItem.id)
        }
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.container, fragment, null)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        dismiss()
    }
}