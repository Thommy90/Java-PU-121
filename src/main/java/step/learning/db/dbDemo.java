package step.learning.db;

import com.mysql.cj.jdbc.Driver;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class dbDemo {
    private Random random = new Random();
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
        // подготовленные (prepared) запросы - можно считать тимчасовым сохранением процедур
        // (скомпелированные запросы, которые сохраняются с боку СУБД)
        // Идкя - запрос комплелируется и скомпелированный код сохраняется у СУБД в течении
        // открытого соединения. В течении этого времени запрос можно повторить, в тч с другими
        // параметрами
        String sql = "INSERT INTO jpu121_randoms(`id`, `val_int`, `val_str`,`val_float`)" +
                " VALUES( UUID(), ?, ?, ? )";
        /*
        Места для вариативных данных меняются на ? , неизмененные данный (типо UUID) остаются в запросе
        Если значения берутся в лапки, то знаки ? все равно без лопок
         */
        try ( PreparedStatement prep = this.connection.prepareStatement( sql )) {
            prep.setInt (1, random.nextInt());
            prep.setString(2, random.nextInt() + "");
            prep.setDouble(3, random.nextDouble());
            prep.execute();
            System.out.println("INSER OK");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void ShowRandomsCount(){
        try ( PreparedStatement prep = this.connection.prepareStatement( "SELECT COUNT(id) FROM jpu121_randoms")) {
            ResultSet res = prep.executeQuery();
            res.next();
            /*        Java                                                  DB
   prepareStatement                                           proc_tmp() {
"SELECT COUNT(id) FROM jpu121_randoms"     ---------------->    return SELECT COUNT(id) FROM jpu121_randoms }
   res = prep.executeQuery()               ----------------> CALL proc_tmp() --> Iterator#123
       (res==Iterator#123)               <-- Iterator#123 --
   res.next()                              ---------------->   Iterator#123.getNext() - береться 1й рядок
                                        <-- noname: 7 ------
   res.getInt( 1 ) - 7
 */
            System.out.println("Rows count: " + res.getInt(1));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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

        ensureCreated();
        //insertPrepared();
        //showRandoms();
        //ShowRandomsCount();
        rowsCountInSegment(10, 100);
        ShowRowsCountInSegment(10, 100);
        this.disconect();
    }
    private void ensureCreated(){
        String sql = "CREATE TABLE IF NOT EXISTS jpu121_randoms (" +
                "`id` CHAR(36) PRIMARY KEY, " +
                "`val_int` INT," +
                "`val_str` VARCHAR(256)," +
                "`val_float` FLOAT" +
                ")";
        System.out.println("ensureCreated: ");
        try(Statement statement = this.connection.createStatement()){  // ADO.NET: SqlCommand
            statement.executeUpdate( sql ); //executeUpdate - для запроса без возвращения
            System.out.println("OK");
        } catch (SQLException e) {
            System.err.println( e.getMessage());
        }
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
