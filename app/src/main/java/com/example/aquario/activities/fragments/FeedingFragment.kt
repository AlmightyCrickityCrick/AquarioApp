package com.example.aquario.activities.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.aquario.R
import com.example.aquario.activities.AddAquariumActivity
import com.example.aquario.activities.FeedingTimeSetActivity
import com.example.aquario.utils.GlobalUser
import com.example.aquario.utils.setMenuButton
import com.google.android.material.switchmaterial.SwitchMaterial

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FeedingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FeedingFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_feeding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fragment_toolbar)
        val toolbarFragmentName = toolbar.findViewById<TextView>(R.id.toolbar_fragment_name)
        toolbarFragmentName.text = getString(R.string.feeder)
        activity?.let { setMenuButton(toolbar, it) }

        val feed = view.findViewById<ImageView>(R.id.btn_feed)

        val switch = view.findViewById<SwitchMaterial>(R.id.switch_feeder)
        switch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    feed.setImageResource(R.drawable.feed_icon_grey)
                } else {
                    feed.setImageResource(R.drawable.feed_icon_orange)
                }
            }

        val btnAdd = view.findViewById<LinearLayout>(R.id.btn_set_timer)
        btnAdd.setOnClickListener {
            activity?.let {
                var intent = Intent(it, FeedingTimeSetActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FeedingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FeedingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}