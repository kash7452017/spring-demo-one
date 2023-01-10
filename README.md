
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
**透過(Setter Injection)方法進行依賴注入**
```
<bean id="myCricketCoach"
    class="com.luv2code.springdemo.CricketCoach">
    	
	<!-- set up setter injection -->
    <property name="fortuneService" ref="myFortuneService" />
</bean>
```
**等同於傳統方式**
```
RandomFortuneService myFortuneService = new RandomFortuneService();
CricketCoach myCricketCoach = new CricketCoach();
myCricketCoach.setFortuneService(myFortuneService);
```
**加入Setter方法注入依賴，Spring會依照XML配置自動為我們注入**
```
public class CricketCoach implements Coach {

	private FortuneService fortuneService;
	
	// create a no-arg constructor
	public CricketCoach()
	{
		System.out.println("CricketCoach: inside no-arg constructor");
	}

	// our setter method
	public void setFortuneService(FortuneService fortuneService) {
		System.out.println("CricketCoach: inside setter method - setFortuneService");
		this.fortuneService = fortuneService;
	}
}
```

## Spring Bean Scopes and Lifecycle(作用域與生命週期)
引用https://blog.csdn.net/fuzhongmin05/article/details/73389779
>Spring中的bean默認都是單例的，對於Web應用來說，Web容器對於每個用戶請求都創建一個單獨的Sevlet線程來處理請求
>>五種作用域中，request、session和global session三種作用域僅在基於web的應用中使用（不必關心你所採用的是什麼web應用框架），只能用在基於web的Spring ApplicationContext環境
>>* Singleton是單例類型，就是在創建起容器時就同時自動創建了一個bean的對象，不管你是否使用，他都存在了，每次獲取到的對像都是同一個對象
>>* Prototype是原型類型，它在我們創建容器的時候並沒有實例化，而是當我們獲取bean的時候才會去創建一個對象，而且我們每次獲取到的對像都不是同一個對象
>>* 當一個bean的作用域為Request，表示在一次HTTP請求中，一個bean定義對應一個實例；即每個HTTP請求都會有各自的bean實例，針對每次HTTP請求，Spring容器會根據loginAction bean的定義創建一個全新的LoginAction bean實例，且該loginAction bean實例僅在當前HTTP request內有效，因此可以根據需要放心的更改所建實例的內部狀態，而其他請求中根據loginAction bean定義創建的實例，將不會看到這些特定於某個請求的狀態變化。當處理請求結束，request作用域的bean實例將被銷毀
>>* 當一個bean的作用域為Global Session，表示在一個全局的HTTP Session中，一個bean定義對應一個實例。典型情況下，僅在使用portlet context的時候有效。該作用域僅在基於web的Spring ApplicationContext情形下有效，global session作用域類似於標準的HTTP Session作用域，不過僅僅在基於portlet的web應用中才有意義。Portlet規範定義了全局Session的概念，它被所有構成某個portlet web應用的各種不同的portlet所共享。在global session作用域中定義的bean被限定於全局portlet Session的生命週期範圍內
>## 定義 init 和 destroy 方法
**TrackCoach實現DisposableBean接口並提供destroy() 方法**
```
public class TrackCoach implements Coach, DisposableBean {

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
```
>如果bean的scope設為prototype時，當容器關閉時，destroy方法不會被調用。對於prototype作用域的bean，有一點非常重要，那就是Spring不能對一個prototype bean的整個生命週期負責：容器在初始化、配置、裝飾或者是裝配完一個prototype實例後，將它交給客戶端，隨後就對該prototype實例不聞不問了。不管何種作用域，容器都會調用所有對象的初始化生命週期回調方法。但對prototype而言，任何配置好的析構生命週期回調方法都將不會被調用。清除prototype作用域的對象並釋放任何prototype bean所持有的昂貴資源，都是客戶端代碼的職責
```
<!-- define the dependency -->
<bean id="myFortuneService"
	class="com.luv2code.springdemo.HappyFortuneService">
</bean>
    
<bean id = "myCoach"
    class="com.luv2code.springdemo.TrackCoach"
    scope="prototype">
    	
    <!-- set up constructor injection -->
    <constructor-arg ref="myFortuneService" />
</bean>
```
**自定義bean處理在MyCustomBeanProcessor類中處理**
```
public class MyCustomBeanProcessor implements BeanPostProcessor, BeanFactoryAware, DisposableBean {

	private BeanFactory beanFactory;

	private final List<Object> prototypeBeans = new LinkedList<>();

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		// after start up, keep track of the prototype scoped beans. 
		// we will need to know who they are for later destruction
		
		if (beanFactory.isPrototype(beanName)) {
			synchronized (prototypeBeans) {
				prototypeBeans.add(bean);
			}
		}

		return bean;
	}


	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}


	@Override
	public void destroy() throws Exception {

		// loop through the prototype beans and call the destroy() method on each one
		
        synchronized (prototypeBeans) {

        	for (Object bean : prototypeBeans) {

        		if (bean instanceof DisposableBean) {
                    DisposableBean disposable = (DisposableBean)bean;
                    try {
                        disposable.destroy();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        	prototypeBeans.clear();
        }
        
	}
}
```
