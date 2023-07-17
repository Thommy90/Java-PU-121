package step.learning.control;

public class ControlDemo {
    public void run(){
        System.out.println("Control Demo run");
        /*
        Типи данных: reference-type
         */
        int[] arr1 = {1,2,3,4,5};
        int[] arr2 = new int[] {1,2,3,4,5};
        int[] arr3 = new int[5];
//        for (int i = 0; i < arr1.length; i++) {
//            System.out.print(" " + arr1[i]);
//        }
//        System.out.println();

        new MultiplicationTable().multiple();

        // String
        String str1 = "Hello";  // String pool - одне значення стає
        String str2 = "Hello";  // одним об'єктом
        String str3 = new String("Hello");
        String str4 = new String("Hello");
        if( str1 == str2 ) {  // через пул рядків це насправді один і той самий об'єкт
            System.out.println("str1 == str2");
        }
        else {
            System.out.println("str1 != str2");
        }
        if( str3 == str4 ) {  // об'єкти рівні тільки якщо це один об'єкт (за посиланням)
            System.out.println("str3 == str4");
        }
        else {
            System.out.println("str3 != str4");
        }
        if( str3.equals( str4 ) ) {  // порівняння за контентом
            System.out.println("str3 equals str4");
        }
        else {
            System.out.println("str3 !equals str4");
        }
    }
}

/*
Control flow instructions - инструкции управленния викнонням
операторы умнового та циклического викнонная, а также перехода
между инструкциями
 */