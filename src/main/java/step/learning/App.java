package step.learning;

import step.learning.control.ControlDemo;
import step.learning.db.dbDemo;
import step.learning.files.FileDemo;
import step.learning.files.GsonDemo;
import step.learning.oop.Library;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        //new ControlDemo().run();
       // new Library().showCatalog();
       // new FileDemo().run();
       // new GsonDemo().run();
        new Library().save();
        new Library().load("library.txt");
       // new dbDemo().run();
    }

}

/*
Java Вступ

Java - ООП мова програмування, на сьогодні курується Oracle
Мова типу "транслятор" - компілюється у байт-код (проміжний
код), який виконується спеціальною платформою (JRE -
Java Runtime Environment) або JVM (Virtual Machine)
Ця платформа встановлюється як окреме ПЗ. Для перевірки
можна виконати у терміналі команду
java -version

У Java гарна "зворотна" сумісність - старші платформи нормально
виконують код, створений ранніми платформами
Є визначна версія - Java8 (1.8), яка оновлюється, але не
модифікується. На ній працює більшість програмних комплексів
типу ЕЕ

Java SE (Standart Edition) - базовий набір
Java EE (Enterprise Edition) - базовий набір + розширені засоби

Для створення програм необхідний додатковий пакет - JDK (Java
Development Kit)

Після встановлення JRE, JDK встановлюємо IDE (Intellij Idea)
Створюємо новий проєкт.
Як правило, проєкти базуються на шаблонах, орієнтованих на
простоту збирання проєкту - підключення додаткових модулів,
формування команд компілятору та виконавцю, тощо
Поширені системи - Maven, Gradle, Ant, Idea
При створенні нового проєкту - вибираємо Maven Archetype
тип - org.apache.maven.archetypes:maven-archetype-quickstart

После создания проекта конфигурируем запуск (от начала настроен
щапуск current file) - edit configuration - create new -
Application  -- вводим название (App) и выбираем главный класс
 - App
 */

/*
Java - интерпретированный язык, которая файлы .java (код на выходе)
компилирует в файлы .class(промежуточный код), который исполняется
JVM командой java.exe step.learning.App

На видмину от Студии отдельные окна консоли не создаются
виведння (та введення) проводится через IDE, окно Run

в Java строгая привязка к структуре файлов и папок
- папка - это package (пакет, аналог namespace)
название папки один - до - каждого должен збигатысь с
именем пакету. Принято lowercase для название пакетов.
- файл - это класс. Ограничения - один файл - один паблин класс
в одном файле может быть несколько классов, но только один public, те
видимый в этом файле, а так же есть внутршние (Nested) классы - классы
в классах. Название класса может один-до-каждого сходится с именем файла,
для имен классов принято CapitalCamelCase
 */