import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Acceso {

    // atributos

    private Connection conexion;
    private Statement statement;
    private ResultSet resultSet;
    ResultSetMetaData metaDatos;

    // constructor

    public Acceso() {
        this.conexion = null;
        this.statement = null;
        this.resultSet = null;
    }

    // método para establecer la conexión con la base de datos

    public void conectar() {

        try {
            Class.forName("com.mysql.jdbc.Driver"); // carga del driver JDBC
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // establecer los datos para la conexion

        String url = "jdbc:mysql://localhost:3306/aad";
        String usuario = "root";
        String contrasena = "";

        // conectar y crear el statement

        try {
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            statement = conexion.createStatement();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // método para cerrar los recursos abiertos

    public void desconectar() {

        // cierra el statement

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // cierra la conexión

        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // método para ejecutar una consulta SQL y mostrar los resultados

    public void realizarConsulta(String consulta) {

        try {
            resultSet = statement.executeQuery(consulta); // ejecuta la consulta y almacena los datos en resultSet

            // procesamiento de los resultados
            boolean seguir = resultSet.next();
            while (seguir) {
                System.out.println(resultSet.getInt(1) + " " +
                        resultSet.getString(2) + " " +
                        resultSet.getString(3) + " " +
                        resultSet.getInt(4));
                seguir = resultSet.next();
            }
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // método para mostrar información adicional sobre los metadatos de los resultados

    public void realizarConsultaFormateada(String consulta) {
        try {

            // obtiene y muestra metadatos

            resultSet = statement.executeQuery(consulta);
            metaDatos = resultSet.getMetaData();
            System.out.println("Número de columnas: " + metaDatos.getColumnCount());
            System.out.println("Nombre de la tabla: " + metaDatos.getTableName(1));
            System.out.println("Nombre del esquema o catálago: " + metaDatos.getCatalogName(1));
            System.out.println();
            System.out.println("Datos de la tabla " + metaDatos.getTableName(1) + " con columnas");
            System.out.println("-----------------------------------");

            // muestra los nombres de las columnas

            for (int i = 1; i < metaDatos.getColumnCount() + 1; i++) {
                System.out.print(metaDatos.getColumnName(i) + " ");
            }

            System.out.println();
            realizarConsulta(consulta); // muestra los datos de las filas
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
