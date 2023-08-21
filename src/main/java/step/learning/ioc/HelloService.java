package step.learning.ioc;

/**
 * Демонстрация работы со службами
 */
public class HelloService implements GreetingService{
    public void sayHello(){
        System.out.println("Hello, World");
    }
}
