package com.wildanpurnomo.timefighter.ui.main.leaderboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ListenerRegistration
import com.wildanpurnomo.timefighter.R
import com.wildanpurnomo.timefighter.data.leaderboard.LeaderboardViewModel
import com.wildanpurnomo.timefighter.data.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_leaderboard.*

class LeaderboardFragment : Fragment() {

    private lateinit var mRecyclerViewAdapter: LeaderboardRVAdapter

    private val mUserViewModel: UserViewModel by activityViewModels()

    private lateinit var mLeaderboardViewModel: LeaderboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRecyclerViewAdapter = LeaderboardRVAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLeaderboardViewModel = ViewModelProvider(this).get(LeaderboardViewModel::class.java)

        fragLeaderboardRV.adapter = mRecyclerViewAdapter
        fragLeaderboardRV.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel.getLeaderboardQuery().observe(viewLifecycleOwner, Observer {
            mLeaderboardViewModel.setQueryListener(it)
        })

        mLeaderboardViewModel.getLeaderboardList().observe(viewLifecycleOwner, Observer {
            mRecyclerViewAdapter.updateDataset(it)
        })

        mLeaderboardViewModel.getToastMsg().observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }
}