package com.luv2code.springdemo;

public class myGolfCoach implements Coach {

	private FortuneService fortuneService;
	
	public myGolfCoach(FortuneService theFortuneService) {
		fortuneService = theFortuneService;
	}
	
	@Override
	public String getDailyWorkout() {
		return "Practice your putting skills for 2 hours today";
	}

	@Override
	public String getDailyFortune() {
		return fortuneService.getFortune();
	}

}
