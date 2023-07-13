package step.learning.oop;

import java.util.Date;

public class Newspaper extends Literature{
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
}
