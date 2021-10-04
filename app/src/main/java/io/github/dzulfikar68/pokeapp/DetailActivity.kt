package io.github.dzulfikar68.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.dzulfikar68.pokeapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpDesc.adapter = DescPagerAdapter(supportFragmentManager)
        binding.tlDesc.setupWithViewPager(binding.vpDesc)
    }
}