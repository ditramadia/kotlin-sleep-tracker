package com.example.sleeptracker.sleepquality

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sleeptracker.R
import com.example.sleeptracker.database.SleepDatabase
import com.example.sleeptracker.databinding.FragmentSleepQualityBinding

class SleepQualityFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepQualityBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sleep_quality, container, false)

        // Create an instance of the ViewModel Factory.
        val application = requireNotNull(this.activity).application
        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        // Get a reference to the ViewModel associated with this fragment.
        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)
        val sleepQualityViewModel = ViewModelProvider(this, viewModelFactory)[SleepQualityViewModel::class.java]

        // Binding.
        binding.sleepQualityViewModel = sleepQualityViewModel

        // Observe Live Data from SleepQualityViewModel
        sleepQualityViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                sleepQualityViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}