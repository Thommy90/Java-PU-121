package step.learning.oop;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Literature> funds;

    public Library() {
        funds = new ArrayList<>();
        funds.add(new Book("Art of Programming", "D. Knuth"));
    }

    public void showCatalog(){
        System.out.println( "Catalog" );

    }
}

/*
Проект "Библиотека"
Библиотека - хранилище литературы разного типа: газеты, журналы, книги, тд
По каждому виду литературы должен быть лист-каталог с названием и другими данными
 */
