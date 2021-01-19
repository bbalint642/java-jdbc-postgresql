package hu.skillversum.jdbc;

import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.Properties;

public class Main {

    private static final String DATABASE_URL="jdbc:postgresql://localhost:5432/demo";
    private static final String DATABASE_USER_NAME="postgres";
    private static final String DATABASE_PASSWORD="Admin123";

    public static void main(String[] args) {
        System.out.println("Elindul az alkalmazás");

        Properties props = new Properties();

        props.setProperty("user",DATABASE_USER_NAME);
        props.setProperty("password",DATABASE_PASSWORD);
        try
        {
            Connection conn= DriverManager.getConnection(DATABASE_URL, props);
            System.out.println("Rá tudunk kapcsolódni az adatbázisra");


            insert(conn);
            delete(conn);
            update(conn);
            selectDemo(conn);

            conn.close();



        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void insert(Connection conn) throws SQLException {

        System.out.println("Megtörténik az insert");
        PreparedStatement preparedStatement=conn.prepareStatement("insert into dog_owner (first_name, last_name) values(?,?)");
        preparedStatement.setString(1, "asdf3");
        preparedStatement.setString(2, "wdsa3");

        preparedStatement.execute();
    }
    private static void delete(Connection conn) throws SQLException {

        System.out.println("Megtörténik a törlés");
        PreparedStatement preparedStatement=conn.prepareStatement("delete from dog_owner where first_name=?");
        preparedStatement.setString(1, "asdf2");

        preparedStatement.execute();
    }
    private static void update(Connection conn) throws SQLException {

        System.out.println("Megtörténik az update");
        PreparedStatement preparedStatement=conn.prepareStatement("update  dog_owner set first_name=? where first_name=?");
        preparedStatement.setString(1, "asdf3"+System.currentTimeMillis());
        preparedStatement.setString(2, "asdf3");

        preparedStatement.execute();
    }

    private static void selectDemo(Connection connection) throws SQLException {

        System.out.println("Megtörténik a selectDemo");
        PreparedStatement preparedStatement=connection.prepareStatement("select id from dog_owner where first_name=?");
        preparedStatement.setString(1,"Bálint");

        ResultSet resultSet= preparedStatement.executeQuery();
        Long id = null;
        while(resultSet.next()){
            id = resultSet.getLong("id");
        }
        System.out.println("Bálint id-ja: "+id);
        selectDogDemo(id, connection);

    }
    private static void selectDogDemo(Long ownerId, Connection connection) throws SQLException {
        PreparedStatement preparedStatement=connection.prepareStatement("select id, name, color, species from dog where owner_id=? order by id");
        preparedStatement.setLong(1, ownerId);
        ResultSet resultSet= preparedStatement.executeQuery();

        while(resultSet.next()){

            Dog dog = new Dog();
            dog.setId(resultSet.getLong("id"));
            dog.setName(resultSet.getString("name"));
            dog.setColor(resultSet.getString("color"));
            dog.setSpecies(resultSet.getString("species"));


            System.out.println("Kutya adatai, id: "+dog.getId()+", name:"+dog.getName()+", color:"+dog.getColor()+", species:"+dog.getSpecies());

        }

    }
}
