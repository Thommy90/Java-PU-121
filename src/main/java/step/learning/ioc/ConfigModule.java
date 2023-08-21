package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Модуль конфигурации инжектор - тут отмечаются соотношение
 * интерфейсов и их реализации, а также другие способы поставки
 */
public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        // связывание за типом
        bind(GreetingService.class).to(HiService.class);

        // именованое связывание (несколько реализаций одного типа)
        bind(PartingService.class).annotatedWith(Names.named("bye")).to(ByeService.class);
        bind(PartingService.class).annotatedWith(Names.named("goodbye")).to(GoodByeService.class);
        bind(IHashService.class).annotatedWith(Names.named("MD5")).to(MD5.class);
        bind(IHashService.class).annotatedWith(Names.named("SHA")).to(SHA.class);
    }
}
