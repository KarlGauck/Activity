package com.karlg.activity.logic.workout.traversal

import com.karlg.activity.logic.workout.data.ExerciseSequence

class ExerciseSequenceTraversalStatus (
    val EXERCISE_NUMBER: Int,
    val REPETITION_COUNT: Int,
    val exerciseSequence: ExerciseSequence,
    private val currentExerciseIndex: Int = 0,
    private val currentRepetition: Int = 0
) {
    fun gotoNextSequence(): ExerciseSequenceTraversalStatus? {
        val newExerciseIndex = (currentExerciseIndex + 1) % EXERCISE_NUMBER
        val newRepetition = currentRepetition + if (newExerciseIndex == 0) 1 else 0

        if (newExerciseIndex == 0 && newRepetition == REPETITION_COUNT)
            return null
        return ExerciseSequenceTraversalStatus(EXERCISE_NUMBER, REPETITION_COUNT, exerciseSequence, newExerciseIndex, newRepetition)
    }

    fun getCurrentNode() = exerciseSequence.exercises[currentExerciseIndex]
}
