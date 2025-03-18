package com.karlg.activity.logic.workout.traversal

import com.karlg.activity.logic.workout.data.ExerciseData
import com.karlg.activity.logic.workout.data.ExerciseSequence

class WorkoutTraverser (
    exerciseSequence: ExerciseSequence
){
    private val traversalStatusStack = mutableListOf<ExerciseSequenceTraversalStatus>()

    private fun ExerciseSequence.generateInitialStatus(): ExerciseSequenceTraversalStatus = ExerciseSequenceTraversalStatus (
        this.exercises.size,
        this.repetitions,
        this,
        0,
        0
    )

    private var MutableList<ExerciseSequenceTraversalStatus>.last: ExerciseSequenceTraversalStatus
        set(value) {
            if (this.isEmpty())
                throw NoSuchElementException()
            if (value != null)
                this[this.size-1] = value
        }
        get() = if (this.isEmpty()) throw NoSuchElementException() else this[this.size-1]

    init {
        traversalStatusStack.add (
            exerciseSequence.generateInitialStatus()
        )
        fillStackToNextExercise(traversalStatusStack)
    }

    /**
     * Fill the stack until the next Node is an Exercise
     */
    private fun fillStackToNextExercise(traversalStatusStack: MutableList<ExerciseSequenceTraversalStatus>)
    {
        if (traversalStatusStack.isEmpty())
            return

        var workoutNode = traversalStatusStack.last.getCurrentNode()
        while (workoutNode is ExerciseSequence) {
            val newSequenceStatus = workoutNode.generateInitialStatus()
            traversalStatusStack.add(newSequenceStatus)
            workoutNode = newSequenceStatus.getCurrentNode()
        }

    }

    /**
     * When a sequence is finished it returns to last sequence
     * that is not finished yet
     */
    private fun clearFinishedSequences(traversalStatusStack: MutableList<ExerciseSequenceTraversalStatus>) {
        var currentStatus = traversalStatusStack.last
        var nextExercise = currentStatus.gotoNextSequence()

        while (nextExercise == null)
        {
            traversalStatusStack.removeLast()
            if (traversalStatusStack.isEmpty())
                return
            currentStatus = traversalStatusStack.last
            nextExercise = currentStatus.gotoNextSequence()
        }

    }

    fun getCurrentExercise(): ExerciseData? = traversalStatusStack.lastOrNull()?.getCurrentNode() as ExerciseData?

    fun gotoNextExercise(): ExerciseData? {
        val newTraversalStatus = traversalStatusStack.last.gotoNextSequence() ?: run {
            clearFinishedSequences(traversalStatusStack)
            if (traversalStatusStack.isEmpty())
                return null
            traversalStatusStack.last.gotoNextSequence()
        }

        traversalStatusStack.last = newTraversalStatus!!
        fillStackToNextExercise(traversalStatusStack)

        val workoutNode = traversalStatusStack.last.getCurrentNode()
        return workoutNode as ExerciseData
    }

}
