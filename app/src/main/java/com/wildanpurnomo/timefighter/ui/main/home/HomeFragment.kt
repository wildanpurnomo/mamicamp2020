package com.wildanpurnomo.timefighter.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wildanpurnomo.timefighter.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.item_animation_fall_down)
        fragHomeCardPractice.startAnimation(anim)
        fragHomeCardSingle.startAnimation(anim)
        fragHomeCardMulti.startAnimation(anim)

        fragHomeCardSingle.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_single_player_page)
        }

        fragHomeCardPractice.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_practice_page)
        }
    }
}