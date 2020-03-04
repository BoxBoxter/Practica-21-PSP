import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        InterficieHorari horari = null;
        Scanner scan = new Scanner(System.in);

        String ip, ciutat, hora;

        //inicializar connexió.
        while (true) {

            System.out.println("Indiqui la IP del servidor");
            ip = scan.nextLine();
            System.out.println();
            //Revisa que la direcció IP sigui correcte, en cas afirmatiu es connecta
            try {
                // creant el stub
                Registry registro = LocateRegistry.getRegistry(ip, 5550);
                horari = (InterficieHorari) registro.lookup("Horari");
                break;

            } catch (NotBoundException e) {

                System.err.println("Quin error es? " + e);

            } catch (RemoteException e) {
                System.err.println("ERROR: Dirección IP " + ip + " Time Out");
            }
        }

        while (true) {

            System.out.println("Introdueixi el nom de la poblacio (adeu per sortir)");
            ciutat = scan.nextLine();
            //En cas que el client es despedeixi
            if (ciutat.toLowerCase().equals("adeu"))
                break;


            try {
                System.out.println(horari.darHora(ciutat) + "\n");
            } catch (RemoteException e) {
                System.out.println("Vols introduïr una nova ciutat a la BD? (si/no)");
                String opcio = scan.nextLine();

                if(opcio.equals("si")) {
                    System.out.println("Por favor indique la fecha de hoy en el siguiente formato dd/mm/yyyy ");

                    String fechaAñadir = scan.nextLine();
                    System.out.println("Por favor indique ahora la hora con en formato 24h de esta forma hh:mm");
                    String horaAñadir = scan.nextLine();
                    try {
                        if (horari.nuevaCiudad(ciutat, fechaAñadir + " " + horaAñadir)) {
                            System.out.println("Ciudad añadida con exito");
                            System.out.println(horari.darHora(ciutat) + "\n");
                        } else {
                            System.out.println("Ha ocurrido un error añadiendo la ciudad a la base de datos... Por favor intentelo de nuevo");
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            /*

             * En cas que el client es despedeixi

             */

            if (ciutat.toLowerCase().equals("adeu"))

                break;

        }
    }
}
