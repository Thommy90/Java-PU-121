package step.learning.ioc;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IocApp {
//    @Inject
//    private GreetingService helloService;
//    @Inject @Named("bye")
//    private PartingService byeService;
//    @Inject @Named("goodbye")
//    private PartingService goodbyeService;


    private final GreetingService helloService;
    private final PartingService byeService;
    private final PartingService goodbyeService;
    private final IHashService SHA;
    private final Random random;
    @Inject @Named("planetConnection")   // Можлива "змішана" інжекція - і через конструктор
    private String connectionString ;    // і через поля - обидві працюють одночасно
    @Inject @Named("logFilename")
    private String logFilename ;
    @Inject @Named("java.util")
    private Random random2;
    private final Logger logger; // Guice автоматически поставляет Logger (java.util)
private final IHashService MD5;
    @Inject
    public IocApp(GreetingService helloService,
                  @Named("bye") PartingService byeService,
                  @Named("goodbye") PartingService goodbyeService,
                  @Named("SHA")  IHashService sha,
                  @Named("java.util") Random random,
                  Logger logger, @Named("MD5") IHashService md5) {
        this.helloService = helloService;
        this.byeService = byeService;
        this.goodbyeService = goodbyeService;
        SHA = sha;
        this.random = random;
        this.logger = logger;
        MD5 = md5;
    }

    public void run(){
        //System.out.println("App works");
       // helloService.sayHello();
        //byeService.sayGoodbye();
        //goodbyeService.sayGoodbye();
//        Scanner in = new Scanner(System.in);
//        System.out.print("Введите строку: ");
//        String text = in.nextLine();
//        MD5.GetHash(text);
//        SHA.GetHash(text);
        if (connectionString == null)
        {
            logger.log(Level.SEVERE, "SEVERE: Служба connectionString NULL");
        }
        else {
            //System.out.println(connectionString);
            logger.log(Level.INFO, "INFO: Служба connectionString инициализирована");
        }
        logFilename = null;
        if (logFilename == null)
        {
            logger.log(Level.SEVERE, "SEVERE: Служба logFilename NULL");
        }
        else {
            //System.out.println(logFilename);
            logger.log(Level.INFO, "INFO: Служба logFilename инициализирована");
        }
        if (random == null)
        {
            logger.log(Level.SEVERE, "SEVERE: Служба random NULL");
        }
        else {
            logger.log(Level.INFO, "INFO: Служба random инициализирована");
        }
        if (helloService == null)
        {
            logger.log(Level.SEVERE, "SEVERE: Служба helloService NULL");
        }
        else {
            logger.log(Level.INFO, "INFO: Служба helloService инициализирована");
        }
        if (byeService == null)
        {
            logger.log(Level.SEVERE, "SEVERE: Служба byeService NULL");
        }
        else {
            logger.log(Level.INFO, "INFO: Служба byeService инициализирована");
        }
        if (goodbyeService == null)
        {
            logger.log(Level.SEVERE, "SEVERE: Служба goodbyeService NULL");
        }
        else {
            logger.log(Level.INFO, "INFO: Служба goodbyeService инициализирована");
        }
        if (MD5 == null)
        {
            logger.log(Level.SEVERE, "SEVERE: Служба MD5 NULL");
        }
        else {
            logger.log(Level.INFO, "INFO: Служба MD5 инициализирована");
        }
        if (SHA == null)
        {
            logger.log(Level.SEVERE, "SEVERE: Служба SHA NULL");
        }
        else {
            logger.log(Level.INFO, "INFO: Служба SHA инициализирована");
        }
//        System.out.println( logFilename ) ;
//        System.out.println( random.nextInt());
//        System.out.println(random.hashCode() + " " + random2.hashCode());
//        logger.log(Level.INFO, "Logger info");
//        logger.log (Level.WARNING, "Logger warning");
//        logger.log(Level.SEVERE, "Logger severe");
    }
}
/*
Принципы инжекции
а) если класс видимый в проекте (есть часть проекта), то инжектор может использовать обект данного
класса без дополнительных конфигураций, достаточно добавить анотацию @Inject
(Приватные поля инжектируются)
 Недостаток инжекции через поля - они остаются измененными (не константы),
 они могут быть изменены специально или случайно
 б) через конструктор - возможная инициализация неизменяемых (final) полей
    переваги
    - защита посланий на службы
    - защита на создание обьекта без служб (наявнисть конструктора прибирает за замовчуванням)
 */
/*
Инверсия управления (Inversion of control), иньекция зависимостей ( DI - dependency injection)

Управлением чем? Жизненным циклом обьектов
- Без инверсии (обычное управление)
    instance = new Type() - создание обьекта
    instance = null - "уничтожение" обьекта - отдать до GC
- C инверсией
    service <- Type [Singletin] (Регистрация)
    @inject instance  (Резолюция) --- Способ IoC даст посылание на обьект

SOLID
O - open/close - дополняй, но не изменяй
D - DIP depencdency inversion (!не injection) principle
    не рекомендуется зависимость от конкретного типа,
    рекомендуется от интерфейса (или абстрактного типа)

Пример:
    Разрабатываем новую версию (улучшаем шифратор Cipher)
    - (не рекомендуется) - вносим изменение в класс Cipher
    - (рекомендовано) - создаем потомка CipherNew и
    изменяем зависимость от Cipher на CipherNew
    = для упрощения второго этапа используется DIP:
        вместо того чтобы создавать зависимость от класса (Cipher)
        private Cipher cipher;
        рекомендовано создать интерфейс и использовать зависимость через него
        ICipher
        Cipher : ICipher           / new CipherNew()
        private ICipher cipher;    \ new Cipher()
        а в IoC отмечаем, что под интерфейсом ICipher будет класс Cipher
        это сильно упростит замену новой реализации на CipherNew и
        обратные изменинения

Техника:
До проекта добавляется инвертор (инжектор), например Google Guice или Spring
Стартовая точка настраивает сервисы, решает (resolve) первый класс
(часто называют App)
Другие классый вместо создание новых обьектов-служб указывают зависимости от них

- Google Guice - Maven зависимость  <!-- https://mvnrepository.com/artifact/com.google.inject/guice -->
- класс IocApp (этот файл)
- идем в main.App
 */