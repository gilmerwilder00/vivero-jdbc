package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO {
    protected Connection conexion = null;
    protected ResultSet resultSet = null;
    protected Statement statement = null;
    private final String HOST = "127.0.0.1"; // localhost
    private final String PORT = "3306";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String DATABASE = "vivero";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";

    protected void connectarDataBase() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(DRIVER);
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
            conexion = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    protected void desconectarDataBase() throws SQLException, ClassNotFoundException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    // El primer método se llamará insertarModificarEliminarDataBase, es del tipo
    // protected. Este método recibirá como parámetro una cadena de texto que
    // contendrá la sentencia SQL necesaria para realizar operaciones de inserción,
    // actualización y eliminación en la base de datos. El método se encargará de
    // establecer la conexión con la base de datos, ejecutar la sentencia
    // proporcionada y luego cerrar la conexión.

    protected void insertarModificarEliminarDataBase(String sql) throws Exception {
        try {
            connectarDataBase();
            statement = conexion.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        } finally {
            desconectarDataBase();
        }
    }

    // El segundo método se llamará consultarDataBase, es del tipo protected. Este
    // método recibirá como parámetro una cadena de texto que contendrá la sentencia
    // SQL necesaria para realizar una consulta en la base de datos. El método se
    // encargará de establecer la conexión con la base de datos, ejecutar la
    // sentencia proporcionada y luego cerrar la conexión.

    protected void consultarDataBase(String sql) throws Exception {

        try {
            connectarDataBase();
            statement = conexion.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }finally {
            desconectarDataBase();
        }
    }
}