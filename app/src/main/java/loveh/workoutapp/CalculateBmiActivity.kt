package loveh.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import loveh.workoutapp.databinding.ActivityCalculateBmiBinding

class CalculateBmiActivity : AppCompatActivity() {
    private var binding: ActivityCalculateBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbBmiActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "This is a title"
        }
        binding?.tbBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener {
            if (weightAndHeightHaveValues()) {
                val weightValue: Float = binding?.etWeight?.text.toString().toFloat()
                val heightValue: Float = binding?.etHeight?.text.toString().toFloat() / 100
            val bmi = weightValue / (heightValue * heightValue)
                binding?.tvBmiResult?.text = bmi.toInt().toString()
                binding?.tvHeader?.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Please enter a valid weight and height.", Toast.LENGTH_LONG).show()
            }

        }
    }


    private fun weightAndHeightHaveValues(): Boolean {
        return binding?.etWeight?.text.toString().isNotEmpty() && binding?.etHeight?.text.toString().isNotEmpty()
    }
}