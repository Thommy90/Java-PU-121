package step.learning.threading;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.*;

public class PercentDemo {
    private final Random random = new Random();
    private double sum;
    private final Object sumLocker = new Object();
    private final ExecutorService pool = Executors.newFixedThreadPool(5);

    public void run4(){
        for (int i = 0; i < 15; i++) {
            printHello(i + 1);
        }
        try {
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        pool.shutdown();
    }
    public void run(int month){
        for (int i = 0; i < month; i++) {
            Percent(i + 1);
        }
        try {
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Final sum: " + sum);
        pool.shutdown();
    }
    void Percent(int month){
        sum = 100;
        pool.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            double localSum;
            double sumBefore;
            long p;
            synchronized (sumLocker) {
                sumBefore = sum;
                localSum = sum;
                localSum *= BigDecimal.valueOf(1 + Math.random()).setScale(2, RoundingMode.HALF_UP).doubleValue();
                sum = localSum;
                p = Math.round((localSum / sumBefore - 1) * 100);
            }
            System.out.println("Month " + month + " - Inflation: " + p + " %  - sum before: " + sumBefore + " - finished with sum = " + localSum);
        });
    }
    Future<?> printHello( int num){
        return pool.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { }
            System.out.println("Hello " + num);
        });
    }
    public void run3() {
        Future<String> task1 = taskString();
        System.out.println("Parallel");
        try {
            String result = task1.get();
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        pool.shutdown();
    }

    Future<String> taskString() {
       return pool.submit( () -> {
            Thread.sleep(1500);
            return "Hello";
        });
    }
    public void run2(){
        sum = 100;
        int month = 12;
        Thread[] threads = new Thread[month];
        for (int i = 0; i < month; i++) {
            threads[i] = new PercentAdder(i + 1);
            threads[i].start();
        }
        try {
            for (int i = 0; i < month; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Final sum: " + sum);
    }

    class PercentAdder extends Thread{
        private final int month;

        public PercentAdder(int month) {
            this.month = month;
        }

        @Override
        public void run() {
             double localSum;
            double sumBefore;
            long p;
                System.out.println("Month " + month + " started");
                int receivingTime =  200; // random.nextInt(300) +
                try {
                    Thread.sleep(receivingTime);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
//            localSum *= 1.1;
//            sum = localSum;
            synchronized (sumLocker) {
                sumBefore = sum;
                localSum = sum;
                localSum *= BigDecimal.valueOf(1 + Math.random()).setScale(2, RoundingMode.HALF_UP).doubleValue();
                sum = localSum;
                p = Math.round((localSum / sumBefore - 1) * 100);
            }
            System.out.println("Month " + month + " - Percent: " + p + " %  - sum before: " + sumBefore + " - finished with sum = " + localSum);
        }
    }
}

