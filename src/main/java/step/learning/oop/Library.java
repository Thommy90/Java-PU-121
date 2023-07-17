package step.learning.oop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Library {
    private List<Literature> funds;

    public Library() {
        funds = new ArrayList<>();
        funds.add(new Book("Art of Programming", "D. Knuth"));
        funds.add(new Journal("Nature", 123));
        funds.add(new Newspaper("Daily mail", new Date(2020 - 1900, 11 - 1, 20)));
        funds.add(new Booklet("Booklet", "Thommy"));
        funds.add(new Hologram("Twin Towers", new Date(2001 - 1900, 9 - 1, 11)));
        funds.add(new Hologram("Death Star (Star Wars)", new Date(1977 - 1900, 5 - 1, 25)));
        funds.add(new Hologram("Tesla Cybertruck", new Date(2023 - 1900, 7 - 1, 17)));
        funds.add(new Poster("The Terminator", 1984));
    }

    public void showCatalog(){
        System.out.println( "Catalog" );
        for(Literature literature : funds){
            System.out.println(literature.getCard());
        }
        System.out.println("----------Copyable----------");
        this.showCopyable();
        System.out.println("----------Non-Copyable----------");
        this.showNonCopyable();
        System.out.println("----------Periodic----------");
        this.showPeriodic();
        System.out.println("----------Book Authors----------");
        this.showBooksAuthors();
        System.out.println("----------Exposed----------");
        this.showExpo();
        System.out.println("----------Non-Exposed----------");
        this.showNonExpo();
    }

    public void showCopyable(){
        for(Literature literature : funds){
            if(literature instanceof Copyable) {
                System.out.println(literature.getCard());
            }
        }
    }
    public void showNonCopyable(){
        for(Literature literature : funds){
            if(! (literature instanceof Copyable)) {
                System.out.println(literature.getCard());
            }
        }
    }
    public void showPeriodic(){
        for(Literature literature : funds){
            if(literature instanceof Periodic) {
                System.out.print(literature.getCard());
                System.out.printf(" Periodic with period: %s\n",
                ((Periodic)literature).getPeriod() );
            }
        }
    }

    public void showBooksAuthors(){
        for(Literature literature : funds){
            if(literature instanceof Book) {
                System.out.println(((Book) literature).getAuthor());
            }
        }
    }

    public void showExpo() {
        for (Literature literature : funds) {
            if (literature instanceof Expo) {
                System.out.println(literature.getCard());
            }
        }
    }

    public void showNonExpo() {
        for (Literature literature : funds) {
            if (!(literature instanceof Expo)) {
                System.out.println(literature.getCard());
            }
        }
    }
}

/*
Проект "Библиотека"
Библиотека - хранилище литературы разного типа: газеты, журналы, книги, тд
По каждому виду литературы должен быть лист-каталог с названием и другими данными
 */
