package com.example.medicaleducation.feature_programs.presentation.pdf_view

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.medicaleducation.R
import com.example.medicaleducation.common.Constants.TAG
import com.example.medicaleducation.databinding.FragmentProgramsListBinding
import com.example.medicaleducation.databinding.FragmentViewPdfBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


class ViewPdfFragment : Fragment() {
    private var _binding: FragmentViewPdfBinding? = null
    private val binding get() = _binding!!
    val viewModel: ViewPdfViewModel by viewModels()
    val args: ViewPdfFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewPdfBinding.inflate(inflater, container, false)
        val view = binding.root

        //Get Args Pass to ViewModel to download file
        val url = args.url
        val fileName = args.fileName
        viewModel.downloadPdf(requireContext(), url, fileName)
        observeViewModel()
        return view
    }

    private fun observeViewModel() {
        viewModel.filepath.observe(viewLifecycleOwner){
            //Open pdf from Filepath
            Log.d(TAG,"FilePath : ${Environment.DIRECTORY_DOWNLOADS}/medical-pdf/$it")
            val downloadedFile = File("${Environment.DIRECTORY_DOWNLOADS}/medical-pdf/",it)
            showPdfFromFile(downloadedFile)
        }
    }

    private fun showPdfFromFile(file: File) {
        binding.pdfView.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, _ ->
                Toast.makeText(
                    requireContext(),
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(viewModel.broadcastReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(viewModel.broadcastReceiver)
    }
}