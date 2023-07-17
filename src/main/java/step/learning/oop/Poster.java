package step.learning.oop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Poster extends Literature implements Expo, Copyable{
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private int year;

    public Poster(String title, int year) {
        super();//делегирование конструктора - демонстрация
        super.setTitle(title);
        this.setYear( year ); ;
    }

    @Override
    public String getCard() {
        return String.format("Poster: %s '%d'", super.getTitle(), this.getYear());
    }


}
