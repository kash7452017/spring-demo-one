package com.luv2code.springdemo;

import java.util.Random;

public class RandomWorkoutService implements WorkoutService {

	// create an array of strings
	private String[] randomWorkouts =
		{
			"Run a hard 5k",
			"Practice your putting skills for 2 hours today",
			"Practice fast bowling for 15 minutes"
		};
	
	// create a random number generator
	private Random myrondom = new Random();	
		
	@Override
	public String getWorkout() {
		// pick a random string from the array
		int index = myrondom.nextInt(randomWorkouts.length);
		return randomWorkouts[index] + " (From RandomWorkoutService)";
	}

}
