package com.example.silvanadorantescode.taxi_location_android.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import com.example.silvanadorantescode.taxi_location_android.R

/**
 * Created by SilvanaDorantes on 21/03/20.
 */
class TaxiLocationToolbarView : LinearLayout {
    var title: String = ""
    lateinit var iconLeft: ImageView
    lateinit var iconRight: ImageView
    lateinit var titleHeader: TextView
    lateinit var logoHeader: ImageView
    lateinit var count: TextView

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    fun init(@Nullable set: AttributeSet?, context: Context){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.taxi_location_toolbar, this, true)
        iconLeft = findViewById<View>(R.id.icon_left) as ImageView
        iconRight = findViewById<View>(R.id.icon_rigth) as ImageView
        titleHeader = findViewById<View>(R.id.title_header) as TextView
        logoHeader = findViewById<View>(R.id.logo_header) as ImageView
        count = findViewById<View>(R.id.count_notify) as TextView


        if (context.obtainStyledAttributes(set, R.styleable.TaxiLocationToolbarView, 0, 0).getBoolean(R.styleable.TaxiLocationToolbarView_menu_toolbar, true)) {
            iconLeft.setImageDrawable(context.resources.getDrawable(R.drawable.ic_chevron_left_white_24dp))
        } else {
            iconLeft.setImageDrawable(context.resources.getDrawable(R.drawable.ic_menu_white_24dp))
        }
        if (context.obtainStyledAttributes(set, R.styleable.TaxiLocationToolbarView, 0, 0).getBoolean(R.styleable.TaxiLocationToolbarView_icon_left_toolbar, false)) {
            iconLeft.visibility = View.VISIBLE
        } else {
            iconLeft.visibility = View.INVISIBLE
        }



        when (context.obtainStyledAttributes(set, R.styleable.TaxiLocationToolbarView, 0, 0).getInt(R.styleable.TaxiLocationToolbarView_icon_right_toolbar, 0)) {
            0 -> iconRight.setImageDrawable(context.resources.getDrawable(R.drawable.ic_bell_outline_white_24dp))
            1 -> iconRight.setImageDrawable(context.resources.getDrawable(R.drawable.ic_bell_white_24dp))

        }


        if (context.obtainStyledAttributes(set, R.styleable.TaxiLocationToolbarView, 0, 0).getBoolean(R.styleable.TaxiLocationToolbarView_icon_right_toolbar, false)) {
            iconRight.visibility = View.VISIBLE
        } else {
            iconRight.visibility = View.INVISIBLE
        }

        if (context.obtainStyledAttributes(set, R.styleable.TaxiLocationToolbarView, 0, 0).getString(R.styleable.TaxiLocationToolbarView_title_toolbar) != null)
            title = context.obtainStyledAttributes(set, R.styleable.TaxiLocationToolbarView, 0, 0).getString(R.styleable.TaxiLocationToolbarView_title_toolbar) as String
        if (title != null && !title.equals("")) {
            titleHeader.setText(title)
            titleHeader.visibility = View.VISIBLE
            logoHeader.visibility = View.GONE

        } else {
            titleHeader.visibility = View.GONE
            logoHeader.visibility = View.VISIBLE
        }

        if (context.obtainStyledAttributes(set, R.styleable.TaxiLocationToolbarView, 0, 0).getBoolean(R.styleable.TaxiLocationToolbarView_logo_text, false)) {

        } else {

        }

    }

    fun iconLeftOnClickListener(onClickListener: View.OnClickListener) {
        iconLeft.setOnClickListener(onClickListener)
    }



    fun iconRigthOnClickListener(onClickListener: View.OnClickListener) {
        iconRight.setOnClickListener(onClickListener)
    }




}