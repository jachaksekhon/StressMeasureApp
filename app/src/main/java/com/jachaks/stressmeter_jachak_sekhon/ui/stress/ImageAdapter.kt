package com.jachaks.stressmeter_jachak_sekhon.ui.stress

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class ImageAdapter(private val context: Context, private val imageItems: List<ImageItem>) : BaseAdapter() {

    override fun getCount(): Int = imageItems.size

    override fun getItem(position: Int): Any = imageItems[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = ImageView(context)
        imageView.setImageResource(imageItems[position].imageResourceId)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.layoutParams = ViewGroup.LayoutParams(225, 300)
        return imageView
    }
}


