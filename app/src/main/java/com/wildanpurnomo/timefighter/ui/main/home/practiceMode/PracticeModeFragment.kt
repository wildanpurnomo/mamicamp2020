package com.wildanpurnomo.timefighter.ui.main.home.practiceMode

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
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
import com.wildanpurnomo.timefighter.ui.main.home.singlePlayerMode.GameFinishedDialog
import kotlinx.android.synthetic.main.fragment_practice_mode.*
import kotlinx.android.synthetic.main.fragment_single_player_mode.*
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig

class PracticeModeFragment : Fragment() {

    private lateinit var mPracticeModeViewModel: PracticeModeViewModel

    private val mUserViewModel: UserViewModel by activityViewModels()

    private lateinit var mGameFinishedDialog: GameFinishedDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mPracticeModeViewModel = ViewModelProvider(this).get(PracticeModeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_practice_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragPracticeModeBtn.setOnClickListener {
            mPracticeModeViewModel.startCountDownTimer()
            mPracticeModeViewModel.setButtonPosition()
            mPracticeModeViewModel.addScore()

            fragPracticeModeBtnDummy.x = fragPracticeModeBtn.x
            fragPracticeModeBtnDummy.y = fragPracticeModeBtn.y
            fragPracticeModeBtnDummy.visibility = View.VISIBLE
            val animX = ObjectAnimator.ofFloat(fragPracticeModeBtnDummy, "x", fragPracticeETScore.x)
            val animY = ObjectAnimator.ofFloat(fragPracticeModeBtnDummy, "y", fragPracticeETScore.y)
            val animAlpha = ObjectAnimator.ofFloat(fragPracticeModeBtnDummy, "alpha", 0.5f, 0f)
            AnimatorSet().setDuration(500).apply {
                playTogether(animX, animY, animAlpha)
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        fragPracticeModeBtnDummy.visibility = View.GONE
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                    }

                    override fun onAnimationStart(p0: Animator?) {
                    }
                })
                start()
            }
        }

        fragPracticeModeBtn.doOnLayout {
            mPracticeModeViewModel.setDefaultButtonPos(Pair(it.x, it.y))
        }

        fragPracticeParent.doOnLayout {
            mPracticeModeViewModel.setLayoutSize(Pair(it.width - 200, it.height - 200))
        }


        mPracticeModeViewModel.getButtonPosition().observe(viewLifecycleOwner, Observer {
            val animX = ObjectAnimator.ofFloat(fragPracticeModeBtn, "x", it.first)
            val animY = ObjectAnimator.ofFloat(fragPracticeModeBtn, "y", it.second)
            AnimatorSet().setDuration(100).apply {
                playTogether(animX, animY)
                start()
            }
        })

        mPracticeModeViewModel.getCurrentScore().observe(viewLifecycleOwner, Observer {
            fragPracticeETScore.text = getString(R.string.your_score, it)
            val blinkAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.blink)
            fragPracticeETScore.startAnimation(blinkAnim)
        })

        mPracticeModeViewModel.getTimeLeft().observe(viewLifecycleOwner, Observer {
            fragPracticeETTime.text = getString(R.string.time_left, it)
        })

        mPracticeModeViewModel.getGameFinishedSignal().observe(viewLifecycleOwner, Observer {
            val finalScore = mPracticeModeViewModel.getCurrentScore().value ?: 0
            mGameFinishedDialog = GameFinishedDialog.newInstance(finalScore)
            mGameFinishedDialog.show(
                requireActivity().supportFragmentManager,
                GameFinishedDialog::class.java.simpleName
            )
        })

        displayShowCase()
    }

    private fun displayShowCase() {
        val showCaseConfig = ShowcaseConfig()
        showCaseConfig.delay = 100

        val sequence = MaterialShowcaseSequence(requireActivity(), "TUTORIALLLLL")
        sequence.setConfig(showCaseConfig)
        sequence.addSequenceItem(fragPracticeModeBtn, getString(R.string.tutorial_button), "OK")
        sequence.addSequenceItem(fragPracticeETTime, getString(R.string.tutorial_time), "OK")
        sequence.addSequenceItem(fragPracticeETScore, getString(R.string.tutorial_score), "OK")
        sequence.start()
    }
}