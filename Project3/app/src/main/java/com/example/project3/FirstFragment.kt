package com.example.project3

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.project3.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.current.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment,
                bundleOf("CITY" to binding.editCity.text.toString())
            )
        }
        binding.forecast.setOnClickListener {
            this.findNavController().navigate(
                R.id.action_FirstFragment_to_thirdFragment,
                bundleOf("CITY" to binding.editCity.text.toString())
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_FirstFragment_to_SecondFragment -> {

                true
            }
            R.id.thirdFragment -> {
                findNavController().navigate(
                    R.id.action_FirstFragment_to_thirdFragment,
                    bundleOf("CITY" to binding.editCity.text.toString())
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}