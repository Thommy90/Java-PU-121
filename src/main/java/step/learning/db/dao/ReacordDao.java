package step.learning.db.dao;

import step.learning.db.dto.Record;

import java.sql.*;
import java.util.*;

public class ReacordDao {
    private final Random random;

    private final Connection connection;

    public ReacordDao(Random random, Connection connection) {
        this.random = random;
        this.connection = connection;
    }

    public boolean ensureCreated(){
        String sql = "CREATE TABLE IF NOT EXISTS jpu121_randoms (" +
                "`id` CHAR(36) PRIMARY KEY, " +
                "`val_int` INT," +
                "`val_str` VARCHAR(256)," +
                "`val_float` FLOAT" +
                ")";
        System.out.println("ensureCreated: ");
        try(Statement statement = this.connection.createStatement()){  // ADO.NET: SqlCommand
            statement.executeUpdate( sql ); //executeUpdate - для запроса без возвращения
            return true;
        } catch (SQLException e) {
            System.err.println( e.getMessage());
            return false;
        }
    }
    public int getRandomsCount(){
        try ( PreparedStatement prep = this.connection.prepareStatement( "SELECT COUNT(id) FROM jpu121_randoms")) {
            ResultSet res = prep.executeQuery();
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public boolean insertPrepared(){
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
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Record getById (UUID id){
        if( id == null){
            return null;
        }
        String sql = String.format("SELECT * FROM jpu121_randoms WHERE id='%s' ",
                id.toString() );
        try(Statement statement = this.connection.createStatement() ) {
            ResultSet res = statement.executeQuery( sql );
            res.next();
        return new Record( res );
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
            return null;
    }
    }
    public boolean deleteById (UUID id){
        if( id == null){
            return false;
        }
        if (getById(id) == null) {
        return false;
        }
        String sql = String.format("DELETE FROM jpu121_randoms WHERE id='%s' ",
                id.toString() );
        try(Statement statement = this.connection.createStatement() ) {
            statement.executeUpdate( sql );
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public boolean delete (Record record){
        String sql = String.format("DELETE FROM jpu121_randoms WHERE id='%s' ",
                record.getId() );
        try(PreparedStatement prep = this.connection.prepareStatement(sql) ) {
            prep.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public boolean update (Record record, int num, String str, double num2){
        String sql = String.format("UPDATE jpu121_randoms SET val_int = %d, val_str = '%s'," +
                        " val_float = %f WHERE id='%s' ",
                num,
                str,
                num2,
                record.getId() );
        try(PreparedStatement prep = this.connection.prepareStatement(sql) ) {
            prep.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    public boolean create(Record record){
        String sql = "INSERT INTO jpu121_randoms(`id`, `val_int`, `val_str`,`val_float`)" +
                        " VALUES( ?, ?, ?, ? )";
        try (PreparedStatement prep = this.connection.prepareStatement(sql)) {
            prep.setString(1, record.getId() == null
                    ? UUID.randomUUID().toString()
                    : record.getId().toString()
            );
            prep.setInt(2, record.getValInt());
            prep.setString(3, record.getValStr());
            prep.setDouble(4, record.getValFloat());
            return prep.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    public void createRandomRecord() {
        String sql = String.format(
                Locale.US,  // Для того щоб десятична кома була точкою
                "INSERT INTO jpu121_randoms(`id`, `val_int`, `val_str`,`val_float`)" +
                        " VALUES( UUID(), %d, '%s', %f )",
                random.nextInt(),
                random.nextInt() + "",
                random.nextDouble()
        );
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("INSERT OK");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<Record> getAll(){
        String sql = "SELECT * FROM jpu121_randoms" ;
        try(Statement statement = this.connection.createStatement() ) {
            ResultSet res = statement.executeQuery( sql );
            List<Record> ret = new ArrayList<>();
            while (res.next()){
                ret.add(new Record(res));
            }
            return ret;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
}
