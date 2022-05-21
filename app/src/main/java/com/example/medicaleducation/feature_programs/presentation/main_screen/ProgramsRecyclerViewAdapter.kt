package com.example.medicaleducation.feature_programs.presentation.main_screen

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicaleducation.common.Constants.TAG
import com.example.medicaleducation.databinding.ProgramsListItemBinding
import com.example.medicaleducation.feature_programs.domain.model.Program


class ProgramsRecyclerViewAdapter :
    RecyclerView.Adapter<ProgramsRecyclerViewAdapter.ProgramsViewHolder>() {

    private var programsList = emptyList<Program>()
    private var downloadID: Long = 0

    inner class ProgramsViewHolder(val binding: ProgramsListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID == id) {
                Toast.makeText(
                    context,
                    "Download Completed with id $downloadID",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramsViewHolder {
        return ProgramsViewHolder(
            ProgramsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ProgramsViewHolder, position: Int) {
        holder.binding.apply {
            programTitle.text = programsList[position].title
            programItemRecycler.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ItemsRecyclerViewAdapter(programsList[position].media)
                    .apply {
                        setOnItemClickListener(object :
                            ItemsRecyclerViewAdapter.onItemClickedListener {
                            override fun onItemClick(position: Int) {
                                when (programsList[holder.adapterPosition].media[position].type) {
                                    "pdf" -> {
                                        Toast.makeText(context, "PDF Clicked", Toast.LENGTH_SHORT)
                                            .show()
                                        //CheckRequest Storage permissions

                                        //Pass Arguments
                                        val url =
                                            programsList[holder.adapterPosition].media[position].url
                                        val fileName = url.substring(url.lastIndexOf("/") + 1)


                                        //TODO Check if file exist open it, download if not
                                        //TODO Add Read Check

                                        //downloadPdf(context, url, fileName)
                                        //Todo on download complete open from files then navigate
                                        Navigation.findNavController(root).navigate(
                                            ProgramsListFragmentDirections.actionProgramsListFragmentToViewPdfFragment(
                                                url,
                                                fileName
                                            )
                                        )

                                    }
                                    "video" -> {
                                        Toast.makeText(context, "Video Clicked", Toast.LENGTH_SHORT)
                                            .show()

                                        val url = programsList[holder.adapterPosition].media[position].url
                                        val videoId = url.substring(url.lastIndexOf("=")+1)
                                        val action =
                                            ProgramsListFragmentDirections.actionProgramsListFragmentToViewVideoFragment(videoId)
                                        Navigation.findNavController(root).navigate(action)
                                    }
                                }
                            }
                        })
                    }
            }
        }
    }

    fun setData(programs: List<Program>) {
        this.programsList = programs
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return programsList.size
    }
}