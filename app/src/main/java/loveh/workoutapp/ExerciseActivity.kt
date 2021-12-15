package loveh.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import loveh.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding : ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var progressMax = 10
    private var countDownMillis = 10000L
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        setupRestView()
    }

    private fun setupRestView() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(countDownMillis, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = progressMax - restProgress
                binding?.tvTimer?.text = (progressMax - restProgress).toString()

            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Exercise starting", Toast.LENGTH_SHORT).show()
                startExercise()
            }
        }.start()

    }

    private fun startExercise() {
        binding?.tvTitle?.text = "BURPEES BOII"
        binding?.progressBar?.max = 30
        countDownMillis = 30000L
        restProgress = 0
        progressMax = 30
        restTimer = object : CountDownTimer(countDownMillis, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = progressMax - restProgress
                binding?.tvTimer?.text = (progressMax - restProgress).toString()

            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Exercise finished, well done!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding = null
    }
}