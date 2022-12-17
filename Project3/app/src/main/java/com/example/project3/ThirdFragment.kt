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
import com.example.project3.databinding.FragmentThirdBinding
import com.squareup.picasso.Picasso

class ThirdFragment : Fragment() {

    companion object {
        fun newInstance() = ThirdFragment()
    }

    private lateinit var binding: FragmentThirdBinding
    private lateinit var viewModel: ThirdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ThirdViewModel::class.java)

        var cityName = arguments?.getString("CITY").toString()
        binding.city2.text = cityName
        var queue = Volley.newRequestQueue(context)
        viewModel.currentWeather(queue, cityName)

        val dateObserver = Observer<String> { date -> binding.date2.text = date }
        viewModel.getDate().observe(viewLifecycleOwner, dateObserver)

        binding.currentBtn.setOnClickListener {
            this.findNavController().navigate(
                R.id.action_thirdFragment_to_SecondFragment,
                bundleOf("CITY" to binding.city2.text.toString())
            )
        }

        binding.changeBtn2.setOnClickListener {
            this.findNavController().navigate(
                R.id.action_thirdFragment_to_FirstFragment,
                bundleOf("CITY" to binding.city2.text.toString())
            )
        }

        val listObserver = Observer<ArrayList<Items>>{list->
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = RecyclerAdapter(list)
        }
        viewModel.getList().observe(viewLifecycleOwner,listObserver)
    }

}