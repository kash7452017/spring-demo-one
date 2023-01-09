package com.luv2code.springdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanLifeCycleDemoApp {

	public static void main(String[] args) {
		
		// load the spring configuration file
		ClassPathXmlApplicationContext context =
				new ClassPathXmlApplicationContext("beanLifeCycle-applicationContext.xml");
		
		
		System.out.println("This is theCoach Init");
		// retrieve bean from spring container
		Coach theCoach = context.getBean("myCoach", Coach.class);
		System.out.println(theCoach);
				
		System.out.println(theCoach.getDailyWorkout());
		
		System.out.println("This is theCoach2 Init");
		Coach theCoach2 = context.getBean("myCoach", Coach.class);
		System.out.println(theCoach2);
		
		System.out.println(theCoach2.getDailyWorkout());
		
		// close the context
		context.close();
		
	}

}
