package loveh.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise-table")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
)