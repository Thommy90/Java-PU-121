package step.learning.oop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Newspaper extends Literature implements Copyable, Periodic{
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public Newspaper(String title, Date date) {
        super.setTitle(title);
        this.setDate(date);
    }

    @Override
    public String getCard() {
        return String.format("Newspaper: '%s' %s", super.getTitle(), dateFormat.format(this.getDate()));
    }

    @Override
    public String getPeriod() {
        return "Daily";
    }
}
