package step.learning.threading;

public class ThreadDemo {
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Start");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Finish Thread");
            }
        }).start();

        new Thread( () -> {
            System.out.println("Start Lamva");
        } ).start();
        new Thread(this::forThread).start(); ;

        InfoRunnable info = new InfoRunnable("Hello");
        Thread infoThread = new Thread(info);

        infoThread.start();
        try {
            infoThread.join(); // wait for infoThread (~ await infoThread)
            System.out.println("Output from InfoThread: " + info.getOutputData());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }


        System.out.println("Main finishes");
    }

    private void forThread(){
        System.out.println("Start forThread");
    }

    static class InfoRunnable implements Runnable{
        private final String inputData;
        private String outputData;

        public InfoRunnable(String inputData) {
            this.inputData = inputData;
        }

        public String getOutputData() {
            return outputData;
        }

        @Override
        public void run() {
            System.out.println("Processing" + inputData);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Finish infoThread");
            outputData = "Processing result";
        }
    }
}


/*
Багатопоточність та асинхронність

(А)Синхронність - розподіл коду у часі
 - синхронність (синхронний код) - код виконується послідовно, інструкція за інструкцією
 - асинхронність - можливість у певний проміжок часу одночасного виконання інструкцій

Багатопоточність - програмування з використанням системних об'єктів "потоки"

Багатопроцессність - використання кількох системних об'єктів "процес"

Grid-, Network- технології - використання вузлів, поєднаних у мережу

Багатозадачность - реализация кода (проаграмм) с использованием программных сутностей "задача"
Реализация багатопоточности может быть в одном потоке, а может быть в нескольких
 */
/*Багатопоточність - робота з об'єктами ThreadКонструктор класу приймає те,
що буде виконуватись в окремому потоці, зокремареалізацію інтерфейсу Runnable
(функціональний інтерфейс - і. з однією функцією).
Запускається потік методом start() [ ! метод run() запускає синхронно ] -
!! запуск через start() утворює потік з пріоритетом NORMAL, це значить, що він
не залежить від головного потоку і буде продовжувати роботу після його завершення
(завершення всієї програми)Метод Runnable::run() не приймає параметри,
не повертає значенняДля обміну даними робиться переозначення класу (Thread чи Runnable),
до них додаютьсяполя через які здійснюється прийом та повернення даних!!
Якщо вимагається повернення даних слід забезпечити очікування завершення потоку,інакше потік,
з якого було запущено новий потік, вже завершить свою роботу.Використання багатопоточності:-
Тривалі задачі    Thread t1 = new ConnectionToDb()    t1.start();  запуск підключення до БД
... робимо інші задачі, не пов'язані з БД    t1.join()  коли задачі без БД закінчились,
очікуємо завершення потоку    t1.getConnection() використовуємо його дані
За наявності більшої кількості задач їх стартують у зворотному порядку до
їх тривалості: спочатку найбільш тривала і так далі */