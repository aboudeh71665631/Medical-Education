package com.example.medicaleducation.feature_programs.presentation.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicaleducation.R
import com.example.medicaleducation.databinding.FragmentProgramsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgramsListFragment : Fragment() {
    private var _binding: FragmentProgramsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProgramsListViewModel by viewModels()
    private val recyclerViewAdapter by lazy { ProgramsRecyclerViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProgramsListBinding.inflate(inflater, container, false)
        val view = binding.root
        //Add Connection Test?
        initRecyclerView()
        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        viewModel.response.observe(viewLifecycleOwner) {
            if (it!=null){
                recyclerViewAdapter.setData(it)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it,Toast.LENGTH_LONG).show()
        }
    }

    private fun initRecyclerView() {
        binding.programsMainRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter
        }
    }

}