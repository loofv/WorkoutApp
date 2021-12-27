package loveh.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import loveh.workoutapp.databinding.ItemExerciseBinding

class ExerciseAdapter(
    private val items: ArrayList<ExerciseEntity>
//    private val updateListener: (id: Int) -> Unit,
//    private val deleteListener: (id: Int) -> Unit
):RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemExerciseBinding): RecyclerView.ViewHolder(binding.root) {
        val tvExerciseName = binding.tvExerciseName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]
        holder.tvExerciseName.text = item.name
    }

    override fun getItemCount(): Int {
        return items.size
    }
}