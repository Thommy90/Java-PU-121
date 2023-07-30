package step.learning.oop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Library {
    public List<Literature> getFunds() {
        return funds;
    }

    public void setFunds(List<Literature> funds) {
        this.funds = funds;
    }

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

    public void save(){
        JSONArray jsonArray = new JSONArray();
        Gson gson = new GsonBuilder().create();
        for (Literature literature : funds) {
            JSONObject obj = new JSONObject();
            obj.put("type", literature.getClass().toString()).put("jsonElement", gson.toJson(literature));
            jsonArray.put(obj);
        }
        try( FileWriter writer = new FileWriter("library.txt") ) {
            writer.write( jsonArray.toString());
            System.out.println( "library saved successful" ) ;
        }
        catch( IOException ex ) {
            System.err.println( ex.getMessage() ) ;
        }
    }



    public void load(String name) {
        System.out.println( "library loading from file: " + name ) ;
        funds = null;
        funds = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(name))) {
                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            String jsonString = stringBuilder.toString();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String className = object.getString("type").substring(6);
                try {
                    Type myType = Class.forName(className);
                    Object obj = gson.fromJson(object.getString("jsonElement"), myType);
                    funds.add((Literature) obj);
                } catch (ReflectiveOperationException e) {
                    System.out.println("Error creating instance: " + e.getMessage());
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        showCatalog();
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
