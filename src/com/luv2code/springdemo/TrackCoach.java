package com.luv2code.springdemo;

import org.springframework.beans.factory.DisposableBean;

public class TrackCoach implements Coach, DisposableBean {
	
	// define a private field for the dependency
	private FortuneService fortuneService;
	
	public TrackCoach()
	{
		
	}
	// define a constructor for dependency injection
	public TrackCoach(FortuneService theFortuneService)
	{
		fortuneService = theFortuneService;
	}
	
	@Override
	public String getDailyWorkout() {
		// TODO Auto-generated method stub
		return "Run a hard 5k";
	}

	@Override
	public String getDailyFortune() {
		// TODO Auto-generated method stub
		return "Just Do It:" + fortuneService.getFortune();
	}

	// add an init method
	public void doMystartupStuff()
	{
		System.out.println("TrackCoach: inside method doMyStartupStuff");
	}
	
	
	// add a destroy method
		@Override
		public void destroy() throws Exception {
			System.out.println("TrackCoach: inside method doMyCleanupStuffYoYo");		
		}
}
