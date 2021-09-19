package calculator;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBInitialize {

    //todo писал путь для встроенной бд
    public static final String DB_URL = "jdbc:h2:/a:/Programs/IdeaProjects/CalculatorFX/db/OPERATION";

    public static final String DB_Driver = "org.h2.Driver";

    Connection connection;
    Statement stmt;

    String sql;

    public DBInitialize(){
        init();
    }

    private void init() {
        try {
            Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД
            connection = DriverManager.getConnection(DB_URL);//соединение с БД
            System.out.println("Соединение с СУБД выполнено.");
//
//            stmt = connection.createStatement();
//            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) VALUES (1, 'Paul', 32, 'California', 20000.00 );";
//            stmt.executeUpdate(sql);

            createTable();

        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // обработка ошибки  Class.forName
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("Ошибка SQL !");
        }
    }

    public void close() {
        try {
            connection.close();       // отключение от БД
            System.out.println("Отключение от СУБД выполнено.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Создаем БД */
    private void createTable() {
        try {
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet = metadata.getTables(null, null, "OPERATION",null);

            boolean res = resultSet.next();
            System.err.println("resultSet -- " + res);

            // Если true - таблица существует
            if(res){
                System.out.println("Таблица уже существует");
            } else{
                System.out.println("-- Таблица создана успешно");
                stmt = connection.createStatement();
                sql = "CREATE TABLE OPERATION " +
                        "(ID          INT PRIMARY KEY   NOT NULL," +
                        " OPERATION   VARBINARY              NOT NULL, " +
                        " RESULT      INT)";
                stmt.executeUpdate(sql);
                stmt.close();
                connection.commit();
                System.out.println("-- Таблица создана успешно");
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void insertTable(String strOperation, String resultText){
        try {
            stmt = connection.createStatement();
            System.err.println(strOperation);

//            char[] ch = strOperation.toCharArray();

            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM OPERATION;");
            rs.next();
            int countRows = rs.getInt("count(*)");
            int count = 5;
            if(countRows == count){
                System.err.println("Удалить первое");
                sql = "DELETE from OPERATION where ID=1;";
                stmt.executeUpdate(sql);
                System.err.println("Изменить индексы");
                int i = 2;
                while (i<=count){
                    sql = "UPDATE OPERATION set ID = " + (i-1) +"  where ID=" + (i) + ";";
                    stmt.executeUpdate(sql);
                    i++;
                }
                System.err.println("Записать новое " + strOperation);
                sql = "INSERT INTO OPERATION (ID,OPERATION,RESULT) VALUES (" + count + ", " + "'" + strOperation + "'"+ ", " + resultText + " );";
                System.err.println(sql);
            }else {
                sql = "INSERT INTO OPERATION (ID,OPERATION,RESULT) VALUES (" +
                        (countRows + 1) + ", " + "'" + strOperation + "'"+  ", " + resultText + " );";
            }


            System.err.println(strOperation);

            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<String> selectTable(){
        List<String> stringList = new LinkedList<>();
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM OPERATION;");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String operation = rs.getString("OPERATION");
                int RESULT = rs.getInt("RESULT");
                System.out.println(String.format("ID=%s OPERATION=%s RESULT=%s", id, operation, RESULT));
                stringList.add(operation);
            }
            rs.close();
            stmt.close();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return stringList;
    }
}
