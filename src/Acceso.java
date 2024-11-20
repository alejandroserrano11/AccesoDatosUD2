import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Acceso {
    private Connection conexion;
    private Statement statement;
    private ResultSet resultSet;
    ResultSetMetaData metaDatos;

    public Acceso() {
        this.conexion = null;
        this.statement = null;
        this.resultSet = null;
    }

    public void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // establecer los datos para la conexion
        String url = "jdbc:mysql://localhost:3306/aad";
        String usuario = "root";
        String contrasena = "";

        try {
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            statement = conexion.createStatement();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void desconectar() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void realizarConsulta(String consulta) {
        try {
            resultSet = statement.executeQuery(consulta);
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

    public void realizarConsultaFormateada(String consulta) {
        try {
            resultSet = statement.executeQuery(consulta);
            metaDatos = resultSet.getMetaData();
            System.out.println("Número de columnas: " + metaDatos.getColumnCount());
            System.out.println("Nombre de la tabla: " + metaDatos.getTableName(1));
            System.out.println("Nombre del esquema o catálago: " + metaDatos.getCatalogName(1));
            System.out.println();
            System.out.println("Datos de la tabla " + metaDatos.getTableName(1) + " con columnas");
            System.out.println("-----------------------------------");
            for (int i = 1; i < metaDatos.getColumnCount() + 1; i++) {
                System.out.print(metaDatos.getColumnName(i) + " ");
            }

            System.out.println();
            realizarConsulta(consulta);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
