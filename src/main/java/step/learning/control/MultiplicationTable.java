package step.learning.control;

public class MultiplicationTable {
    public void multiple() {

        int[][] mas = new int[10][10];

        for (int i = 0; i < 10; i++) {
            for (int q = 0; q < 10; q++) {
                mas[i][q] = (i + 1) * (q + 1);
                if( mas[i][q] < 10)
                System.out.print("  " + mas[i][q]);
                else System.out.print(" " + mas[i][q]);
            }
            System.out.println();
        }
    }
}
