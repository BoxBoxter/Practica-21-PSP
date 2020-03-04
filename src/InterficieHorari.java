import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterficieHorari extends Remote {

    public String darHora(String ciudad) throws RemoteException;

    public boolean nuevaCiudad(String ciudad, String hora) throws RemoteException;
}
