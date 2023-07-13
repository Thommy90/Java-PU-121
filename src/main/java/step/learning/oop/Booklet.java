package step.learning.oop;

public class Booklet extends Literature{
    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public Booklet(String title, String publisher) {
        super.setTitle(title);
        this.setPublisher(publisher);
    }

    private String Publisher;
}
