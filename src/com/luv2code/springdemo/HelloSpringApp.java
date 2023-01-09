package com.luv2code.springdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloSpringApp {

	public static void main(String[] args) {

		// load the spring configuration file
		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext.xml");
							
		// retrieve bean from spring container
		Coach theCoach = context.getBean("myCoach", Coach.class);
//		Coach theCoach2 = context.getBean("myCoach2", Coach.class);
		
		// call methods on the bean
//		System.out.println("call methods on the bean");
		System.out.println(theCoach.getDailyWorkout());
//		System.out.println(theCoach2.getDailyWorkout());
		
		// let's call our new method for fortunes
//		System.out.println("let's call our new method for fortunes");
		System.out.println(theCoach.getDailyFortune());
//		System.out.println(theCoach2.getDailyFortune());
		
		// close the context
		context.close();
	}

}
