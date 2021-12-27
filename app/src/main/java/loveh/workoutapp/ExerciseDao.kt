package loveh.workoutapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insert(exerciseEntity: ExerciseEntity)

    @Update
    suspend fun update(exerciseEntity: ExerciseEntity)

    @Delete
    suspend fun delete(exerciseEntity: ExerciseEntity)

    @Query("SELECT * FROM `exercise-table`")
    fun getAllExercises(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM `exercise-table` where id=:id")
    fun getExerciseById(id: Int): Flow<ExerciseEntity>
}