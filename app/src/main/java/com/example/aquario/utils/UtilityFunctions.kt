package com.example.aquario.utils
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.example.aquario.R
import com.example.aquario.activities.MainActivity

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun setMenuButton(toolbar:View, context:FragmentActivity){
    var butMenu = toolbar.findViewById<ImageView>(R.id.toolbar_menu_button)
    butMenu.setOnClickListener {
        (context as MainActivity).mDrawer.openDrawer(Gravity.LEFT)
    }
}

