package com.tiimhealthcare.patientappointment.feature

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.tiimhealthcare.patientappointment.R
import kotlinx.android.synthetic.main.app_toolbar.view.*

class AppToolbar : Toolbar {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        View.inflate(context, R.layout.app_toolbar, this)
    }
}

fun AppToolbar.appToolbarTitle(title: String?) {
    if (!title.isNullOrEmpty())
        toolbarTitle.text = title
}