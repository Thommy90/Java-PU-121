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
    }
}

/*
Control flow instructions - инструкции управленния викнонням
операторы умнового та циклического викнонная, а также перехода
между инструкциями
 */