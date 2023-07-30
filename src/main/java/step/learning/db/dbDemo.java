package step.learning.db;

import com.mysql.cj.jdbc.Driver;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbDemo {
    private String url;
    private String user;
    private String password;
    private com.mysql.cj.jdbc.Driver mysqlDriver;
    private java.sql.Connection connection;
    public void run() {

    }
    public void run2() {
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
