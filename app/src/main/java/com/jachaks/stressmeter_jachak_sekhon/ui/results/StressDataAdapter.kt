package com.jachaks.stressmeter_jachak_sekhon.ui.results

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jachaks.stressmeter_jachak_sekhon.R
import java.util.Date
import java.util.Locale

class StressDataAdapter(private val dataList: List<StressData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_DATA = 1
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeHeaderTextView: TextView = view.findViewById(R.id.timeHeaderTextView)
        val stressHeaderTextView: TextView = view.findViewById(R.id.stressHeaderTextView)
    }

    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timestampTextView: TextView = view.findViewById(R.id.timestampTextView)
        val stressLevelTextView: TextView = view.findViewById(R.id.stressLevelTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_header, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_stress_data, parent, false)
                DataViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.timeHeaderTextView.text = "Time"
                holder.stressHeaderTextView.text = "Stress"
            }
            is DataViewHolder -> {
                val data = dataList[position - 1] // Adjust index for data list

                // Convert timestamp to a formatted string
                val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
                val formattedDate = dateFormat.format(Date(data.timestamp))

                holder.timestampTextView.text = formattedDate
                holder.stressLevelTextView.text = data.stressLevel.toString()
            }
        }
    }

    override fun getItemCount(): Int = dataList.size + 1 // Add 1 for the header

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_DATA
    }
}
