package io.github.dzulfikar68.pokeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.dzulfikar68.pokeapp.databinding.FragmentStatsBinding

class StatsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }
}