
## 控制反轉 (Inversion of Control)
> 傳統方式，我們都是以Class A = new Class的方式創建一個新的物件或類別，當Code越來越多，規模逐漸擴大，導致管理與維護上越來越複雜與困難，透過控制反轉(IoC)，Spring 通過 IoC 容器來管理物件的例項化和初始化（這些物件就是 Spring Bean），以及物件從建立到銷燬的整個生命週期。也就是管理物件和依賴，以及依賴的注入等等。
>> 在XML配置文件中配置好Bean ID，並給予完全類名，Spring將自動分配與管理，當應用程序使用到時為我們創建使用

```
[applicationContext.xml]
<bean id = "myCoach"
    class="com.luv2code.springdemo.BaseballCoach">
</bean>
```

> 透過ClassPathXmlApplicationContext檢索Bean ID並取得實例
>> 從類路徑下的一個或多個xml配置文件中加載上下文定義，適用於xml配置的方式
```
public class HelloSpringApp {

	public static void main(String[] args) {

		// load the spring configuration file
		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext.xml");
							
		// retrieve bean from spring container
		// 此處myCoach即為XML配置檔中的Bean ID
		Coach theCoach = context.getBean("myCoach", Coach.class);
		
		// call methods on the bean
		System.out.println(theCoach.getDailyWorkout());
		
		// let's call our new method for fortunes
		System.out.println(theCoach.getDailyFortune());
		
		// close the context
		context.close();
	}
}
```

## 什麼是Bean?
>Bean 是由 Spring IoC 容器管理的物件，容器就能通過反射的形式將容器中準備好的物件注入（這裡使用的是反射給屬性賦值）到需求的元件中去，簡單來說，Spring IoC 容器可以看作是一個工廠，Bean 相當於工廠的產品。 Spring 配置檔案則告訴容器需要哪些 Bean，當應用程序使用到時，自動為我們分配創建實例並注入

## 依賴注入(Dependency Injection)
>將所需的依賴實例，注入到高階模組中
>>* 建構元注入 (Constructor Injection)
>>* 設值方法注入 (Setter Injection)
>>* 介面注入 (Interface Injection)
```
// 透過(Constructor Injection)進行依賴注入
public class BaseballCoach implements Coach {

	// define a private field for the dependency
	private FortuneService fortuneService;
	
	// define a constructor for dependency injection
	public BaseballCoach(FortuneService theFortuneService)
	{
		fortuneService = theFortuneService;
	}
	
	@Override
	public String getDailyWorkout()
	{
		return "Play the Baseball";
	}

	@Override
	public String getDailyFortune() {
		// use my fortuneService to get a fortune
		return fortuneService.getFortune();
	}
}
```
**在XML配置檔中依賴，Spring實際會為我們創建實例對象，並調用構造函數注入依賴，實際上fortuneService即注入並獲取RandomFortuneService服務**
```
<bean id="myFortuneService"
    class="com.luv2code.springdemo.RandomFortuneService">
</bean>
       
<bean id = "myCoach"
    class="com.luv2code.springdemo.BaseballCoach">
    	
    <!-- set up constructor injection -->
    <constructor-arg ref="myFortuneService" />
</bean>
```
