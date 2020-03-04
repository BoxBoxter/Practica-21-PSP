import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        InterficieHorari horari = null;
        Scanner scan = new Scanner(System.in);

        String ip, ciutat;

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
                System.out.println("Introduïr una nova ciutat a la BD? (si/no)");
                String opcio = scan.nextLine();

                if (opcio.equals("si")) {
                    System.out.println("Introduïr data avui dd/mm/yyyy ");

                    String data = scan.nextLine();
                    System.out.println("Introduïr hora amb format 24h hh:mm");
                    String hora = scan.nextLine();
                    try {
                        if (horari.nuevaCiudad(ciutat, data + " " + hora)) {
                            System.out.println("Ciutat afegida...");
                            System.out.println(horari.darHora(ciutat) + "\n");
                        } else {
                            System.out.println("ERROR: Falla introdcuccio base de dades");
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if (ciutat.toLowerCase().equals("adeu"))

                break;

        }
    }
}
