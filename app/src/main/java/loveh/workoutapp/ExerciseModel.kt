package loveh.workoutapp

data class ExerciseModel(
    public var id: Int,
    public var name: String,
    public var image: Int,
    public var isCompleted: Boolean,
    public var isSelected: Boolean,
)
