package loveh.workoutapp

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import loveh.workoutapp.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding : ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var progressMax = 10

    private var tts: TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExerciseIndex = 0

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        exerciseList = Constants.defaultExerciseList()

        setContentView(binding?.root)

        tts = TextToSpeech(this, this)

        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }



        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    private fun setupExerciseStatusRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView() {

        try {
            val soundURI = Uri.parse("android.resource://loveh.workoutapp/" + R.raw.press_start)
            mediaPlayer = MediaPlayer.create(applicationContext, soundURI)
            mediaPlayer?.isLooping = false
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val nextExerciseName = exerciseList?.get(currentExerciseIndex)?.name
        speakOut(binding?.tvTitle?.text.toString())
        binding?.progressBar?.max = 10
        binding?.ivExerciseImage?.visibility = View.INVISIBLE
        restProgress = 0
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvTitle?.text = "Get ready for: $nextExerciseName"
        setRestProgressBar()
    }

    private var countDownMillis = 1000L
    private fun setRestProgressBar() {
        speakOut("Now you can rest.")
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(countDownMillis, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = progressMax - restProgress
                binding?.tvTimer?.text = (progressMax - restProgress).toString()

            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Exercise starting", Toast.LENGTH_SHORT).show()
                exerciseList!![currentExerciseIndex].isSelected = true
//                exerciseAdapter!!.notifyDataSetChanged()
                exerciseAdapter!!.notifyItemChanged(currentExerciseIndex)
                startExercise()
            }
        }.start()

    }

    private fun startExercise() {
        speakOut("Start your exercise.")
        val curr = exerciseList?.get(currentExerciseIndex)
        binding?.tvTitle?.text = curr?.name
        binding?.progressBar?.max = 30
        countDownMillis = 3000L
        restProgress = 0
        progressMax = 30
        binding?.ivExerciseImage?.visibility = View.VISIBLE
        restTimer = object : CountDownTimer(countDownMillis, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = progressMax - restProgress
                binding?.tvTimer?.text = (progressMax - restProgress).toString()

            }
            override fun onFinish() {
                if (currentExerciseIndex < exerciseList?.size!! -1) {
                    currentExerciseIndex++
                    exerciseList!![currentExerciseIndex].isSelected = false
                    exerciseList!![currentExerciseIndex].isCompleted = true
                    // Testa den här, kanske lika bra och mer effektiv?
                    exerciseAdapter!!.notifyItemChanged(currentExerciseIndex)
//                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                } else {
                    Toast.makeText(this@ExerciseActivity, "Exercises finished, well done!", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
        }

        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("tts", "result was missing or not supported")
            }
        } else {
            Log.e("tts", "init failed, status was: $status")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text,  TextToSpeech.QUEUE_FLUSH, null, "")
    }
}