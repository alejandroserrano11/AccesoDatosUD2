public class App {
    public static void main(String[] args) throws Exception {
        Acceso miAcceso = new Acceso();
        miAcceso.conectar();
        miAcceso.realizarConsulta("SELECT * FROM ALUMNOS");
        System.out.println();
        System.out.println("***********************************");
        System.out.println();
        miAcceso.realizarConsultaFormateada("SELECT * FROM ALUMNOS");
        
        
        



        miAcceso.desconectar();
    }
}
