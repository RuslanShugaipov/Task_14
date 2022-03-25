package com.example.task14

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task14.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val adapter = RVAdapter()
    private val stringList = listOf(
        "asus", "acer", "msi", "hp",
        "dell", "lenovo", "huawei", "alianware"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRV()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClk.setOnClickListener {
            object : CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    changeStyle()
                }
                override fun onFinish() {
                }
            }.start()
        }

        binding.btnGoToProfile.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.navigateToUserProfileFragment)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    private fun initRV() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(this@HomeFragment.context)
        rcView.adapter = adapter
        for (element in stringList) {
            adapter.addString(element)
        }
    }

    fun changeStyle() {
        adapter.clear()
        adapter.changeStyle()
        initRV()
    }
}