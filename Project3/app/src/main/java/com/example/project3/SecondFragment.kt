package com.example.project3

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import com.example.project3.databinding.FragmentSecondBinding
import com.squareup.picasso.Picasso

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }

    private lateinit var binding: FragmentSecondBinding
    private lateinit var viewModel: SecondViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.forecastBtn.setOnClickListener {
            this.findNavController().navigate(
                R.id.action_SecondFragment_to_thirdFragment,
                bundleOf("CITY" to binding.city.text.toString())
            )
        }

        binding.changeBtn.setOnClickListener {
            this.findNavController().navigate(
                R.id.action_SecondFragment_to_FirstFragment,
                bundleOf("CITY" to binding.city.text.toString())
            )
        }

        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        var cityName = arguments?.getString("CITY").toString()
        binding.city.text = cityName
        var queue = Volley.newRequestQueue(context)
        viewModel.currentWeather(queue, cityName)

        val tempObserver = Observer<String> { temp -> binding.temp.text = temp }
        viewModel.getTemp().observe(viewLifecycleOwner, tempObserver)

        val dateObserver = Observer<String> { date -> binding.date.text = date }
        viewModel.getDate().observe(viewLifecycleOwner, dateObserver)

        val descObserver = Observer<String> { desc -> binding.desc.text = desc }
        viewModel.getDesc().observe(viewLifecycleOwner, descObserver)

        val iconObserver = Observer<String> { icon -> Picasso.with(context).load(icon).into(binding.icon) }
        viewModel.getIcon().observe(viewLifecycleOwner, iconObserver)




    }

}