package com.jachaks.stressmeter_jachak_sekhon.ui.results

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.MarkerType
import com.jachaks.stressmeter_jachak_sekhon.R
import com.jachaks.stressmeter_jachak_sekhon.databinding.FragmentResultBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private lateinit var recyclerView: RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dataList = readDataFromCsv()

        val adapter = StressDataAdapter(dataList)
        recyclerView.adapter = adapter

        // create chart + add to view
        val lineChartView = view.findViewById<AnyChartView>(R.id.line_chart_view)

        val data = dataList.mapIndexed { index, stressData ->
            ValueDataEntry(index, stressData.stressLevel)
        }

        // chart creation, data insertion, axis edits
        val cartesian = AnyChart.line()
        cartesian.data(data)
        cartesian.title("A Graph Showing Your Stress Levels")

        val xAxis = cartesian.xAxis(0)
        xAxis.title("Instances")
        xAxis.labels().format("{%Value}")

        val yAxis = cartesian.yAxis(0)
        yAxis.title("Stress Levels")
        yAxis.staggerMode(true)

        val series = cartesian.line(data)
        series.markers().enabled(true)
        series.markers().type(MarkerType.CIRCLE)

        lineChartView.setChart(cartesian)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun readDataFromCsv(): List<StressData> {
        val dataList = mutableListOf<StressData>()

        val fileName = "stress_timestamp.csv"
        val filePath = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)

        try {
            val reader = BufferedReader(FileReader(filePath))

            // skip header
            reader.readLine()

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val parts = line!!.split(",")
                if (parts.size == 2) {
                    val timestamp = parts[0].toLong()
                    val stressLevel = parts[1].toInt()
                    dataList.add(StressData(timestamp.toLong(), stressLevel))
                }
            }

            reader.close()
            // error if file not found
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return dataList
    }

}
