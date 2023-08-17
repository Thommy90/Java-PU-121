package step.learning.db;

import com.mysql.cj.jdbc.Driver;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import step.learning.db.dao.ReacordDao;
import step.learning.db.dto.Record;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class dbDemo {
    private Random random = new Random();
    private ReacordDao recordDao;

    private String url;
    private String user;
    private String password;
    private com.mysql.cj.jdbc.Driver mysqlDriver;
    private java.sql.Connection connection;
    public void insertRandom() {
        System.out.println("How many rows add?");
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();

            JSONObject conf = this.config();
            JSONObject dbconf = conf
                    .getJSONObject("DataProviders")
                    .getJSONObject("PlanetScale");
            url = dbconf.getString("url");
            user = dbconf.getString("user");
            password = dbconf.getString("password");
            // System.out.println(url); System.out.println(user); System.out.println(password);
            if ((connection = this.connect()) == null) {
                return;
            }
        for (int i = 0; i < rows; i++) {
            int numInt = (int) (Math.random() * 999);
            String generatedString = RandomStringUtils.randomAlphanumeric(20);
            double numDouble = Math.random() * 999;
            String sql = "INSERT INTO jpu121_randoms(`id`, `val_int`, `val_str`,`val_float`) VALUES( UUID(), "+ numInt + ", \'"+ generatedString + "\', "+ numDouble + " )";
            try (Statement statement = this.connection.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
            this.disconect();

    }

    private void showRandoms(){
        String sql = "SELECT * FROM jpu121_randoms" ; // ';' в конце SQL команды не нужна
        try (Statement statement = this.connection.createStatement()) {
            ResultSet res = statement.executeQuery(sql); // ADO ~ SqoDataReader
            // ResultSet res - обьект для трансфера данных, что есть результатом запроса
            // Особенность БД - работа с большими данными, что означает отстутсвие одного результата
            // та получения данных строки - за строкой (итерувания)
            // res.next() - получение новой строки (если есть - true, нету - false)
            while ( res.next() ){
                System.out.printf( "%s %d %s %f %n", // данные строки доступные через get-теры
                res.getString( 1 ), // !!! JDBC запрос начинается с 1 !!!
                res.getInt("val_int"), // за именем колонки
                res.getString("val_str"),
                res.getFloat("val_float")
                );
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void ShowRowsCountInSegment(int num1, int num2){
        String sql = "SELECT * FROM jpu121_randoms WHERE (val_int >= " + num1 +") AND (val_int <= " + num2 +")" ;
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            ResultSet res = statement.executeQuery();
            while ( res.next() ){
                System.out.printf( "%s %d %s %f %n",
                        res.getString( 1 ),
                        res.getInt("val_int"),
                        res.getString("val_str"),
                        res.getFloat("val_float")
                );
            }
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void insertPrepared(){
        if (recordDao.insertPrepared())
        {
            System.out.println("Insert OK");
        }
        else{
            System.out.println("Insert false");
        }
    }

    private void deleteById(UUID id){
        if (recordDao.deleteById(id))
        {
            System.out.println("Delete is OK with ID: " + id.toString());
        }
        else{
            System.out.println("Delete is false");
        }
    }

    private void delete(Record record){
        if (recordDao.delete(record))
        {
            System.out.println("Delete is OK with ID: " + record.getId().toString());
        }
        else{
            System.out.println("Delete is false");
        }
    }

    private void update(Record record, int num, String str, double num2){
        if (recordDao.update(record, num, str, num2))
        {
            System.out.println("Update is OK with ID: " + record.getId().toString() +" with new parameters: valInt: "
            + num + " valStr: " + str + " valFloat: " + num2 );
        }
        else{
            System.out.println("Update is false");
        }
    }
    private void showAll (){
        List<Record> records = recordDao.getAll();
        if (records == null){
            System.out.println("Error getting list");
        }
        else{
            for(Record record : records ){
                System.out.println(record);
            }
        }
    }

    private void ShowRandomsCount(){
            int cnt = recordDao.getRandomsCount();
            if (cnt == -1)
            {
                System.out.println("Count error");
            }
            else{
                System.out.println("Rows count: " + cnt);
            }
    }

    private void rowsCountInSegment(int num1, int num2){
        try ( PreparedStatement prep = this.connection.prepareStatement( "SELECT COUNT(id) FROM jpu121_randoms WHERE (val_int >= " + num1 +") AND (val_int <= " + num2 +") ")) {
            ResultSet res = prep.executeQuery();
            res.next();
            System.out.println("Rows count with col 'val_int' from " + num1 + " to " + num2 + ": " + res.getInt(1));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void run() {
        System.out.println("Database Demo");
        JSONObject conf = this.config();
        JSONObject dbconf = conf
                .getJSONObject("DataProviders")
                .getJSONObject("PlanetScale");
        url = dbconf.getString("url");
        user = dbconf.getString("user");
        password = dbconf.getString("password");
        // System.out.println(url); System.out.println(user); System.out.println(password);
        if ((connection = this.connect()) == null) {
            return;
        }
        System.out.println("connection OK");

        recordDao = new ReacordDao(random, connection) ;
       if(recordDao.ensureCreated()) {
           System.out.println("Ensure OK");
       };
        //insertPrepared();
        //showRandoms();
        //ShowRandomsCount();
        //rowsCountInSegment(10, 100);
        //ShowRowsCountInSegment(10, 100);
        Record record = new Record();
        record.setId(UUID.fromString("5b26e51c-12a0-4b4b-853f-d850f313f746"));
        record.setValInt(100);
        record.setValFloat(100.500);
        record.setValStr("Hello");
        recordDao.create(record);
        System.out.println(record.getId());
        System.out.println(
                recordDao.getById(record.getId()
                )
        );
        update(record, 500, "NewStr", 5.5);
        System.out.println("-------------------------------------------------");
      //  showAll();
        System.out.println(
                recordDao.getById(record.getId()
                )
        );
        this.disconect();
    }

    private java.sql.Connection connect() {
        // регистрируем драйвер
        //а) через Class.forName("com.mysql.cj.jdbc.Driver");
        //b) через прямо создание драйвера
        try {
            mysqlDriver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver( mysqlDriver );
            // подключаемся, имея зарегестрированный драйвер
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    private void disconect() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (mysqlDriver != null) {
                DriverManager.deregisterDriver(mysqlDriver);
            }
        } catch (SQLException ignored) {
        }
    }
    private JSONObject config(){
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader("appsettings.json"))){
            int c;
            while((c = reader.read()) != -1){
                sb.append( ( (char) c ));
            }
        }
        catch (IOException ex){
            System.err.println(ex.getMessage());
        }
        return new JSONObject(sb.toString());
    }
}

/*
Работа с базами данных. JDBC.
1. Конфигурация. Работа с вариативными JSON
- подключаем пакеет (зависимость) org.json
- детали работы - в методах config() та run()
2. Коннектор (драйвер подключения)
 - на Maven ищем драйвер (MySQL) для JDBC
 3. Работа с БД
  - подключение

  Настройка IDE
  Datavase (tool-window) - + - DataSource - MySQL -
  option: URL Only
  вводим данные с конфигурации
  Test Connection - Apply - OK
 */


/*
DTO, DAO
DTO - Data Transfer Object - обьект для передачи данных - структура, которы содержит
поля, их аксесоры, конструкторы та утилиты (toString(), to Json(), и тд). Не содержат логику
Аналог - сутность(Entity)

DAO - Data Acess Object - обьект доступа к данным - логика работы с обьектом DTO. Аналог LINQ

Например
UserDTO {
private UUID id; String name;
public UUID getId()...
}
UserDAO{
public UserDTO getUserById(UUID id){...}
}
 */

           /*        Java                                                  DB
   prepareStatement                                           proc_tmp() {
"SELECT COUNT(id) FROM jpu121_randoms"     ---------------->    return SELECT COUNT(id) FROM jpu121_randoms }
   res = prep.executeQuery()               ----------------> CALL proc_tmp() --> Iterator#123
       (res==Iterator#123)               <-- Iterator#123 --
   res.next()                              ---------------->   Iterator#123.getNext() - береться 1й рядок
                                        <-- noname: 7 ------
   res.getInt( 1 ) - 7
 */