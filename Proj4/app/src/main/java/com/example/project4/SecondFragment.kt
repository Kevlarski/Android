package com.example.project4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
//import com.bumptech.glide.Glide
import com.example.project4.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        var jokeType = arguments?.getString("jokeArg").toString().lowercase()


        viewModel.currentJoke(jokeType)


        val setupObserver = Observer<String> { setup -> binding.setup.text = setup }
        viewModel.getSetup().observe(viewLifecycleOwner, setupObserver)

        val punchlineObserver = Observer<String> { punchline -> binding.punchline.text = punchline }
        viewModel.getPunchline().observe(viewLifecycleOwner, punchlineObserver)

//        Glide.with(view).load(R.drawable.img2).into(binding.image)

    //        viewModel.jokeImage()
        //potential image decoding code
//        val imageObserver =
//            Observer<String> { image -> Picasso.with(context).load(image).into(binding.image) }
//
//        viewModel.getImage().observe(viewLifecycleOwner, imageObserver)
//
//        var imgString = viewModel.getImage().value
//        if(imgString!=null){
//            var image = Base64.getDecoder().decode(imgString)
//            var path =
//                Path("/home/kevlar/Desktop/school/Fall22/Mobile_App_Dev/Proj4/app/src/main/res/drawable/d1.png")
//            path.writeBytes(image)
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}