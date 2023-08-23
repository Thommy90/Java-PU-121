package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.util.Random;

/**
 * Модуль конфигурации инжектор - тут отмечаются соотношение
 * интерфейсов и их реализации, а также другие способы поставки
 */
public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        // связывание за типом
        bind(GreetingService.class).to(HiService.class);
        // зв'язування "готових" об'єктів
        bind( String.class )
                .annotatedWith( Names.named( "planetConnection" ) )
                .toInstance( "The Connection String" ) ;
        bind( String.class )
                .annotatedWith( Names.named( "logFilename" ) )
                .toInstance( "The Log Filename" ) ;

        // именованое связывание (несколько реализаций одного типа)
        bind(PartingService.class).annotatedWith(Names.named("bye")).to(ByeService.class);
        bind(PartingService.class).annotatedWith(Names.named("goodbye")).to(GoodByeService.class);
        bind(IHashService.class).annotatedWith(Names.named("MD5")).to(MD5.class);
        bind(IHashService.class).annotatedWith(Names.named("SHA")).to(SHA.class);
    }

    // методы-поставщики
    private Random _random;
    @Provides @Named("java.util")
    Random randomProvider(){
      //  return new Random() ; // ~Transient

        if(_random == null){     // ~Singleton
            _random = new Random();
        }
        return _random;
    }
}
