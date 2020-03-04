import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {

    public static void main(String[] args) {

        try{
            Registry reg = LocateRegistry.createRegistry(5550);
            System.out.println("Creant l'objecte servidor i inscribint-lo al registre");

            Horario horari = new Horario("ZonesHoraries.json");

            reg.rebind("Horari", (InterficieHorari) UnicastRemoteObject.exportObject(horari, 0));
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
