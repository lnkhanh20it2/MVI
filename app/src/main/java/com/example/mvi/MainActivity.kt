package com.example.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvi.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel:NumberViewModel by lazy {
        ViewModelProvider(this)[NumberViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        render()
        binding.apply {
            btnAddNumber.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.intentChannel.send(MainIntent.AddNumber)
                }
            }
        }
    }
    private fun render(){
        lifecycleScope.launch {
            viewModel.state.collect{
                when(it){
                    is MainViewState.Idle -> binding.tvNumber.text = "Idle"
                    is MainViewState.Number -> binding.tvNumber.text = it.number.toString()
                    is MainViewState.Error -> binding.tvNumber.text = it.error

                }
            }
        }
    }
}