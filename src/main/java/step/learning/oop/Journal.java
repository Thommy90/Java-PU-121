package step.learning.oop;

public class Journal extends Literature implements Copyable, Periodic{
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int number;

    public Journal(String title, int number) {
        super();//делегирование конструктора - демонстрация
        super.setTitle(title);
        this.setNumber( number ) ;
    }

    @Override
    public String getCard() {
        return String.format("Journal: %s No. '%d'", super.getTitle(), this.getNumber());
    }
    @Override
    public String getPeriod() {
        return "Monthly";
    }
}
