package loveh.workoutapp

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks = ExerciseModel(
            1,
        "Jumping jacks",
        R.drawable.workoutpic,
        false,
        false
        )
        exerciseList.add(jumpingJacks)

        val pushups = ExerciseModel(
            2,
            "Pushups",
            R.drawable.workoutpic,
            false,
            false
        )
        exerciseList.add(pushups)

        val burpees = ExerciseModel(
            2,
            "Burpees",
            R.drawable.workoutpic,
            false,
            false
        )
        exerciseList.add(burpees)
        return exerciseList
    }
}