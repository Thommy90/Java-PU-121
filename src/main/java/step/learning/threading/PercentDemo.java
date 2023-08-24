package step.learning.threading;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class PercentDemo {
    private final Random random = new Random();
    private double sum;
    public void run(){
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
        private int month;

        public PercentAdder(int month) {
            this.month = month;
        }

        @Override
        public void run(){
           // double localSum = sum;
            System.out.println("Month " + month + " started");
            int receivingTime = random.nextInt(300) + 200;
            try {
                Thread.sleep(receivingTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
//            localSum *= 1.1;
//            sum = localSum;
            double sumBefore = sum;
            sum *= BigDecimal.valueOf(1 + Math.random()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            System.out.println("Month " + month + " - Perecent: " + Math.round((sum/sumBefore - 1)*100) +" %  - sum before: " + sumBefore + " - finished with sum = " + sum) ;
        }
    }
}
