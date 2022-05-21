package com.example.medicaleducation.feature_programs.presentation.video_playback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import com.example.medicaleducation.R
import com.example.medicaleducation.common.Constants
import com.example.medicaleducation.databinding.FragmentProgramsListBinding
import com.example.medicaleducation.databinding.FragmentViewVideoBinding
import com.google.android.youtube.player.*

class ViewVideoFragment : Fragment(), YouTubePlayer.OnInitializedListener {
    private var _binding: FragmentViewVideoBinding? = null
    private val binding get() = _binding!!
    val args: ViewVideoFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewVideoBinding.inflate(inflater, container, false)
        val view = binding.root

        val youTubePlayerSupportFragment = YouTubePlayerSupportFragmentX.newInstance()
        youTubePlayerSupportFragment.initialize(Constants.KEY, this)

        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.youtube_frame, youTubePlayerSupportFragment)
        fragmentTransaction.commit()


        /*TODO
         * -Fix Youtube Player
         * -Fetch PDF from File system if exist
         * -On user click set item as viewed and increase score if viewed before, ignore
         * -Populate API
         * -Add progress bars for fetching data
         */


        return view
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        //Toast.makeText(this,"Initialized Successfully" , Toast.LENGTH_SHORT).show()
        if (!wasRestored) {
            youTubePlayer?.loadVideo(args.videoId)

        } else {
            youTubePlayer?.play()
        }
    }


    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        val REQUEST_CODE = 0
        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(requireActivity(), REQUEST_CODE).show()
        } else {
            Toast.makeText(
                requireContext(),
                "There was an error initializing the YouTube Player : $youTubeInitializationResult",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}