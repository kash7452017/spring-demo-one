package com.luv2code.springdemo;

import java.util.Random;

public class RandomFortuneService implements FortuneService {

	// create an array of strings
	private String[] randomFortunes =
		{
				"Beware of the wolf in sheep's clothing",
				"Diligence is the mother of good luck",
				"The journey is the reward"
		};
	
	// create a random number generator
	private Random myrondom = new Random();	
	
	@Override
	public String getFortune() {
		// pick a random string from the array
		int index = myrondom.nextInt(randomFortunes.length);
		return randomFortunes[index] + " (From RandomFortuneService)";
	}
}
