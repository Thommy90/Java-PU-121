package step.learning.oop;

public abstract class Literature
{
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public abstract String getCard();
}
