package com.wildanpurnomo.timefighter.ui.main.home.singlePlayerMode

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wildanpurnomo.timefighter.utils.SingleLiveEvent

class SinglePlayerModeViewModel(application: Application) : AndroidViewModel(application) {

    private val isGameActive: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    private val initialLayoutSize: MutableLiveData<Pair<Int, Int>> by lazy {
        MutableLiveData<Pair<Int, Int>>()
    }

    private val layoutSize: MutableLiveData<Pair<Int, Int>> by lazy {
        MutableLiveData<Pair<Int, Int>>()
    }

    fun setLayoutSize(value: Pair<Int, Int>) {
        if (initialLayoutSize.value == null) initialLayoutSize.value = value
        layoutSize.value = value
        if (isGameActive.value == true) setButtonPosition()
    }

    private val defaultButtonPosXY: MutableLiveData<Pair<Float, Float>> by lazy {
        MutableLiveData<Pair<Float, Float>>()
    }

    fun setDefaultButtonPos(value: Pair<Float, Float>) {
        if (defaultButtonPosXY.value == null) defaultButtonPosXY.value = value else {
            defaultButtonPosXYAlter.value = value
        }
    }

    private val defaultButtonPosXYAlter: MutableLiveData<Pair<Float, Float>> by lazy {
        MutableLiveData<Pair<Float, Float>>()
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    private val gameFinishedSignal: SingleLiveEvent<Boolean> by lazy {
        SingleLiveEvent<Boolean>()
    }

    fun getGameFinishedSignal(): LiveData<Boolean> {
        return gameFinishedSignal
    }

    private val buttonMoveInterval: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(10)
    }

    private val countDownTimer: MutableLiveData<CountDownTimer> by lazy {
        MutableLiveData<CountDownTimer>()
    }

    fun startCountDownTimer() {
        if (countDownTimer.value == null) {
            val timerInstance = object : CountDownTimer(60000, 1000) {
                override fun onFinish() {
                    resetGame()
                }

                override fun onTick(millisUntilFinished: Long) {
                    val timeLeft = millisUntilFinished / 1000
                    if (timeLeft.toInt() % (buttonMoveInterval.value ?: 0) == 0) setButtonPosition()
                    this@SinglePlayerModeViewModel.timeLeft.value = millisUntilFinished / 1000
                }
            }

            countDownTimer.value = timerInstance
            (countDownTimer.value as CountDownTimer).start()
            isGameActive.value = true
        }
    }

    private fun resetGame() {
        stopTimer()
        gameFinishedSignal.value = true
        buttonMoveInterval.value = 10
        timeLeft.value = 60
        currentScore.value = 0

        if (layoutSize.value == initialLayoutSize.value) setButtonPosition(
            defaultButtonPosXY.value?.first ?: 0F,
            defaultButtonPosXY.value?.second ?: 0F
        ) else setButtonPosition(
            defaultButtonPosXYAlter.value?.first ?: 0F,
            defaultButtonPosXYAlter.value?.second ?: 0F
        )

        isGameActive.value = false
    }

    private fun stopTimer() {
        if (countDownTimer.value != null) {
            (countDownTimer.value as CountDownTimer).cancel()
            countDownTimer.value = null
        }
    }

    private val timeLeft: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>(60)
    }

    fun getTimeLeft(): LiveData<Long> {
        return timeLeft
    }

    private val currentScore: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }

    fun addScore() {
        currentScore.value = currentScore.value?.minus(-1)
    }

    fun getCurrentScore(): LiveData<Int> {
        return currentScore
    }

    private val buttonPosition: SingleLiveEvent<Pair<Float, Float>> by lazy {
        SingleLiveEvent<Pair<Float, Float>>()
    }

    fun setButtonPosition(x: Float = 0F, y: Float = 0F) {
        if (x == 0F && y == 0F) {
            // random new position
            val newPosition = Pair(
                (0..(layoutSize.value?.first ?: 0)).random().toFloat(),
                (0..(layoutSize.value?.second ?: 0)).random().toFloat()
            )
            buttonPosition.value = newPosition
            if (buttonMoveInterval.value != 1) buttonMoveInterval.value =
                buttonMoveInterval.value?.minus(1)
        } else {
            // back to default position
            buttonPosition.value = Pair(x, y)
        }
    }

    fun getButtonPosition(): LiveData<Pair<Float, Float>> {
        return buttonPosition
    }
}