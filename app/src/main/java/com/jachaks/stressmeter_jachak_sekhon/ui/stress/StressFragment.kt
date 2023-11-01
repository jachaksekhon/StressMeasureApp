package com.jachaks.stressmeter_jachak_sekhon.ui.stress

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.jachaks.stressmeter_jachak_sekhon.R
import com.jachaks.stressmeter_jachak_sekhon.SharedViewModel
import com.jachaks.stressmeter_jachak_sekhon.databinding.FragmentStressBinding

class StressFragment : Fragment() {
    private var currentIndex = 0

    private var _binding: FragmentStressBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val images = listOf(
        ImageItem(R.drawable.fish_normal017, 0),
        ImageItem(R.drawable.psm_alarm_clock, 1),
        ImageItem(R.drawable.psm_alarm_clock2, 2),
        ImageItem(R.drawable.psm_angry_face, 5),
        ImageItem(R.drawable.psm_anxious, 4),
        ImageItem(R.drawable.psm_baby_sleeping, 0),
        ImageItem(R.drawable.psm_bar, 1),
        ImageItem(R.drawable.psm_barbed_wire2, 6),
        ImageItem(R.drawable.psm_beach3, 1),
        ImageItem(R.drawable.psm_bird3, 3),
        ImageItem(R.drawable.psm_blue_drop, 0),
        ImageItem(R.drawable.psm_cat, 0),
        ImageItem(R.drawable.psm_clutter, 7),
        ImageItem(R.drawable.psm_clutter3, 8),
        ImageItem(R.drawable.psm_dog_sleeping, 0),
        ImageItem(R.drawable.psm_exam4, 9),
        ImageItem(R.drawable.psm_gambling4, 6),
        ImageItem(R.drawable.psm_headache, 6),
        ImageItem(R.drawable.psm_headache2, 8),
        ImageItem(R.drawable.psm_hiking3, 1),
        ImageItem(R.drawable.psm_kettle, 4),
        ImageItem(R.drawable.psm_lake3, 4),
        ImageItem(R.drawable.psm_lawn_chairs3, 0),
        ImageItem(R.drawable.psm_lonely, 10),
        ImageItem(R.drawable.psm_lonely2, 10),
        ImageItem(R.drawable.psm_mountains11, 0),
        ImageItem(R.drawable.psm_neutral_child, 3),
        ImageItem(R.drawable.psm_neutral_person2, 2),
        ImageItem(R.drawable.psm_peaceful_person, 0),
        ImageItem(R.drawable.psm_puppy, 0),
        ImageItem(R.drawable.psm_puppy3, 0),
        ImageItem(R.drawable.psm_reading_in_bed2, 0),
        ImageItem(R.drawable.psm_running3, 2),
        ImageItem(R.drawable.psm_running4, 2),
        ImageItem(R.drawable.psm_sticky_notes2, 7),
        ImageItem(R.drawable.psm_stressed_cat, 3),
        ImageItem(R.drawable.psm_stressed_person, 8),
        ImageItem(R.drawable.psm_stressed_person12, 7),
        ImageItem(R.drawable.psm_stressed_person3, 9),
        ImageItem(R.drawable.psm_stressed_person4, 8),
        ImageItem(R.drawable.psm_stressed_person4, 6),
        ImageItem(R.drawable.psm_stressed_person6, 7),
        ImageItem(R.drawable.psm_stressed_person7, 10),
        ImageItem(R.drawable.psm_stressed_person8, 9),
        ImageItem(R.drawable.psm_talking_on_phone2, 2),
        ImageItem(R.drawable.psm_to_do_list, 3),
        ImageItem(R.drawable.psm_to_do_list3, 6),
        ImageItem(R.drawable.psm_wine3, 0),
        ImageItem(R.drawable.psm_work4, 7),
        ImageItem(R.drawable.psm_yoga4, 0)
        )

    private val displayedImages = mutableListOf<ImageItem>()
    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStressBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val gridView = root.findViewById<GridView>(R.id.imageGridView)
        val seeMoreButton = root.findViewById<Button>(R.id.seeMoreButton)

        gridView.adapter = null

        if (displayedImages.isEmpty()) {
            displayedImages.addAll(images.take(16))
        }

        gridView.adapter = ImageAdapter(requireContext(), displayedImages)

        // onclick to start single image activity
        gridView.setOnItemClickListener { _, view, position, _ ->
            val selectedImage = displayedImages[position]
            val intent = Intent(requireContext(), ImageDetailActivity::class.java).apply {
                putExtra("imageResourceId", selectedImage.imageResourceId)
                putExtra("stressLevel", selectedImage.stressLevel)
            }
            sharedViewModel.isSoundPlaying.value = false
            startActivity(intent)
        }

        // onclick to rotate through sets of imgs
        seeMoreButton.setOnClickListener {
            sharedViewModel.isSoundPlaying.value = false
            val startIndex = currentIndex
            val endIndex = startIndex + 16

            if (endIndex <= images.size) {
                displayedImages.clear()
                displayedImages.addAll(images.subList(startIndex, endIndex))
                (gridView.adapter as ImageAdapter).notifyDataSetChanged()
                currentIndex = endIndex
            } else {
                displayedImages.clear()
                displayedImages.addAll(images.subList(0, 16))
                (gridView.adapter as ImageAdapter).notifyDataSetChanged()
                currentIndex = 16
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}