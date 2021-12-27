package loveh.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import loveh.workoutapp.databinding.ActivityHistoryBinding

@InternalCoroutinesApi
class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbHistoryActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding?.tbHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        val exerciseDao = (application as ExerciseApp).db.exerciseDao()
        lifecycleScope.launch {
            exerciseDao.getAllExercises().collect {
                val reversed = it.reversed()
                val list = ArrayList(reversed)

                populateRecyclerView(list)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun populateRecyclerView(exerciseList: ArrayList<ExerciseEntity>) {
        if (exerciseList.isNotEmpty()) {
            val exerciseAdapter = ExerciseAdapter(exerciseList)
            binding?.rvExerciseList?.adapter = exerciseAdapter
        }
    }
}