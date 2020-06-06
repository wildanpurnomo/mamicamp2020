package com.wildanpurnomo.timefighter.ui.main.home.singlePlayerMode

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.doOnLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wildanpurnomo.timefighter.R
import com.wildanpurnomo.timefighter.data.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_single_player_mode.*

class SinglePlayerModeFragment : Fragment() {

    private lateinit var mSinglePlayerModeViewModel: SinglePlayerModeViewModel

    private lateinit var mGameFinishedDialog: GameFinishedDialog

    private val mUserViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mSinglePlayerModeViewModel =
            ViewModelProvider(this).get(SinglePlayerModeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_single_player_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragSingleModeBtn.setOnClickListener {
            mSinglePlayerModeViewModel.startCountDownTimer()
            mSinglePlayerModeViewModel.setButtonPosition()
            mSinglePlayerModeViewModel.addScore()

            fragSingleModeBtnDummy.x = fragSingleModeBtn.x
            fragSingleModeBtnDummy.y = fragSingleModeBtn.y
            fragSingleModeBtnDummy.visibility = View.VISIBLE
            val animX = ObjectAnimator.ofFloat(fragSingleModeBtnDummy, "x", fragSingleETScore.x)
            val animY = ObjectAnimator.ofFloat(fragSingleModeBtnDummy, "y", fragSingleETScore.y)
            val animAlpha = ObjectAnimator.ofFloat(fragSingleModeBtnDummy, "alpha", 0.5f, 0f)
            AnimatorSet().setDuration(500).apply {
                playTogether(animX, animY, animAlpha)
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        fragSingleModeBtnDummy.visibility = View.GONE
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationStart(p0: Animator?) {
                    }
                })
                start()
            }
        }

        fragSingleModeBtn.doOnLayout {
            mSinglePlayerModeViewModel.setDefaultButtonPos(Pair(it.x, it.y))
        }

        fragSingleParent.doOnLayout {
            mSinglePlayerModeViewModel.setLayoutSize(Pair(it.width - 200, it.height - 200))
        }

        mSinglePlayerModeViewModel.getButtonPosition().observe(viewLifecycleOwner, Observer {
            val animX = ObjectAnimator.ofFloat(fragSingleModeBtn, "x", it.first)
            val animY = ObjectAnimator.ofFloat(fragSingleModeBtn, "y", it.second)
            AnimatorSet().setDuration(100).apply {
                playTogether(animX, animY)
                start()
            }
        })

        mSinglePlayerModeViewModel.getCurrentScore().observe(viewLifecycleOwner, Observer {
            fragSingleETScore.text = getString(R.string.your_score, it)
            val blinkAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.blink)
            fragSingleETScore.startAnimation(blinkAnim)
        })

        mSinglePlayerModeViewModel.getTimeLeft().observe(viewLifecycleOwner, Observer {
            fragSingleETTime.text = getString(R.string.time_left, it)
        })

        mSinglePlayerModeViewModel.getGameFinishedSignal().observe(viewLifecycleOwner, Observer {
            val finalScore = mSinglePlayerModeViewModel.getCurrentScore().value ?: 0
            mGameFinishedDialog = GameFinishedDialog.newInstance(finalScore)
            mGameFinishedDialog.show(
                requireActivity().supportFragmentManager,
                GameFinishedDialog::class.java.simpleName
            )
            mUserViewModel.updateLoggedUserMaxScore(finalScore)
        })
    }
}