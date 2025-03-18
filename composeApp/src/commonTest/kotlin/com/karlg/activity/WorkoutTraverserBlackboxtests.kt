package com.karlg.activity

import com.karlg.activity.logic.workout.data.ExerciseSequence
import com.karlg.activity.logic.workout.data.ExerciseData
import com.karlg.activity.logic.workout.traversal.WorkoutTraverser
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class WorkoutTraverserBlackboxtests {

    @Test
    fun test1()
    {
        val sequence = ExerciseSequence(
            listOf(
                ExerciseData(
                    "hi",
                    "hi",
                    3
                ),
                ExerciseData(
                    "hu",
                    "hi",
                    3
                )
            ),
            repetitions = 3
        )

        val traversal = WorkoutTraverser(sequence)

        for (i in 0 until 3) {
            var exercise = traversal.getCurrentExercise()
            assertNotNull(exercise)
            assertEquals("hi", exercise.name)

            traversal.gotoNextExercise()

            exercise = traversal.getCurrentExercise()
            assertNotNull(exercise)
            assertEquals("hu", exercise.name)

            traversal.gotoNextExercise()
        }

        assertNull(traversal.getCurrentExercise())

    }

    @Test
    fun test2() {
        val sequence = ExerciseSequence (
            exercises = listOf(
                ExerciseData("hi", "hi", 3),
                ExerciseSequence (
                    exercises = listOf(
                        ExerciseData("hu", "hi", 3)
                    ),
                    1
                )
            ),
            3
        )
        val traversal = WorkoutTraverser(sequence)
        for (i in 0 until 3) {
            var exercise = traversal.getCurrentExercise()
            assertNotNull(exercise)
            assertEquals("hi", exercise.name)

            traversal.gotoNextExercise()

            exercise = traversal.getCurrentExercise()
            assertNotNull(exercise)
            assertEquals("hu", exercise.name)

            traversal.gotoNextExercise()
        }

        assertNull(traversal.getCurrentExercise())
    }

    @Test
    fun test3() {
        val sequence = ExerciseSequence (
            exercises = listOf(
                ExerciseData("hi", "hi", 3),
                ExerciseSequence (
                    exercises = listOf(
                        ExerciseData("hu", "hi", 3)
                    ),
                    2
                ),
                ExerciseSequence (
                    exercises = listOf(
                        ExerciseData("lol", "lel", 5),
                        ExerciseData("noice", "puh", 3)
                    ),
                    2
                )
            ),
            3
        )
        val traversal = WorkoutTraverser(sequence)
        val names = listOf (
            "hi",
            "hu",
            "hu",
            "lol",
            "noice",
            "lol",
            "noice",
            "hi",
            "hu",
            "hu",
            "lol",
            "noice",
            "lol",
            "noice",
            "hi",
            "hu",
            "hu",
            "lol",
            "noice",
            "lol",
            "noice",
        )
        for (i in 0 until 21)
        {
            val exercise = traversal.getCurrentExercise()
            assertNotNull(exercise)
            assert(exercise is ExerciseData)
            assertEquals(names[i], (exercise as ExerciseData).name)
            traversal.gotoNextExercise()
        }

        assertNull(traversal.getCurrentExercise())

    }


}