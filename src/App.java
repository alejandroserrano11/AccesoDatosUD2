public class App {
    public static void main(String[] args) throws Exception {

        // establezco la conexión con la base de datos
        Acceso miAcceso = new Acceso();
        miAcceso.conectar();

        miAcceso.realizarConsulta("SELECT * FROM ALUMNOS"); // muestra los datos de la tabla alumnos
        System.out.println();
        System.out.println("***********************************");
        System.out.println();
        miAcceso.realizarConsultaFormateada("SELECT * FROM ALUMNOS"); // muestra metadatos y datos de la tabla

        // libera todos los recursos
        miAcceso.desconectar();
    }
}
