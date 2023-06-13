package com.example.aquario.activities.fragments

import android.app.ActionBar.LayoutParams
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.aquario.R
import com.example.aquario.activities.AddAquariumActivity
import com.example.aquario.utils.GlobalUser
import com.example.aquario.utils.setMenuButton
import com.google.android.material.bottomsheet.BottomSheetDialog


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fragment_toolbar)
        val toolbarFragmentName = toolbar.findViewById<TextView>(R.id.toolbar_fragment_name)
        toolbarFragmentName.text = getString(R.string.settings)
        activity?.let { setMenuButton(toolbar, it) }
        setSettingsButtons(view)
    }

    private fun setSettingsButtons(view : View){
        val aquariumName = view.findViewById<TextView>(R.id.tv_current_aquarium)
        aquariumName.text = GlobalUser.aquariums[GlobalUser.currentAquarium].nickname
        val changeAquariumButton = view.findViewById<LinearLayout>(R.id.btn_switch_aquarium)
        changeAquariumButton.setOnClickListener {
            setBottomSheet()
        }
    }

    private fun setBottomSheet(){
        val dialog = activity?.let { it1 -> BottomSheetDialog(it1, R.style.Theme_Aquario_BottomSheetDialog) }
        val bottomView = layoutInflater.inflate(R.layout.fragment_settings_bottom_sheet, null)
        if (dialog != null) {
            setRadioButtons(bottomView, dialog)
            dialog.setCancelable(false)
            dialog.setContentView(bottomView)
            dialog.show()
        }
        val btnAdd = bottomView.findViewById<LinearLayout>(R.id.btn_add_aquarium)
        btnAdd.setOnClickListener {
            activity?.let {
                var intent = Intent(it, AddAquariumActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setRadioButtons(view: View, dialog: BottomSheetDialog){
        val radioGroup = view.findViewById<RadioGroup>(R.id.rg_aquarium_opt)
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_enabled)
            ), intArrayOf(
                Color.BLACK,  // disabled
                ContextCompat.getColor(activity!!, R.color.orange) // enabled
            )
        )


        for (a in GlobalUser.aquariums){
            val button = RadioButton(view.context)
            button.id = View.generateViewId()
            button.text = a.nickname
            button.layoutDirection = View.LAYOUT_DIRECTION_RTL
            button.setTextColor(R.drawable.selector_aquarium_switch_text)
            button.setBackgroundResource(R.drawable.selector_aquarium_switch_background)
            val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            params.bottomMargin = 24
            button.layoutParams = params
            button.setPadding(60, 35, 0, 35)
            button.buttonDrawable = null
            button.compoundDrawablePadding = 10
            var tf = ResourcesCompat.getFont(activity!!.applicationContext, R.font.nunito);
            button.setTypeface(tf, Typeface.BOLD)
            button.setTextColor(resources.getColor(R.color.black))
            if(a.nickname == GlobalUser.aquariums[GlobalUser.currentAquarium].nickname) {
                button.isChecked = true
                button.setTextColor(resources.getColor(R.color.orange))
                button.buttonTintList = colorStateList // set the color tint list
            }
            button.setOnClickListener {
                GlobalUser.currentAquarium = GlobalUser.aquariums.indexOf(a)
                dialog.dismiss()
                activity.let {
                    val fragmentM = activity?.supportFragmentManager
                    fragmentM?.beginTransaction()?.replace(R.id.activity_content, SettingsFragment())?.commit()
                }
            }
            radioGroup.addView(button)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}