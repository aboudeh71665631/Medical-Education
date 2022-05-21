package com.example.medicaleducation.feature_programs.presentation.pdf_view

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


class ViewPdfViewModel : ViewModel() {
    var downloadId:Long = 0

    private val _filepath = MutableLiveData<String>()
    val filepath: LiveData<String>
        get() = _filepath

    private lateinit var fileName : String

    //Function for downloading the pdf file
    fun downloadPdf(context: Context, url: String, fileName: String) {
        val request: DownloadManager.Request = DownloadManager.Request(
            Uri.parse("$url ")
        )
        request.apply {
            setTitle(fileName)
            setMimeType("application/pdf")
            setAllowedOverMetered(true)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                (Environment.DIRECTORY_DOWNLOADS),
                "/medical-pdf/$fileName"
            )
        }
        val downloadManager: DownloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadId = downloadManager.enqueue(request)
        this.fileName = fileName
    }

    var broadcastReceiver = object:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            var id:Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
            if (id == downloadId){
                _filepath.postValue(fileName)
                Toast.makeText(context,"Download Complete",Toast.LENGTH_SHORT).show()
            }
        }
    }

}