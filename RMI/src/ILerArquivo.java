import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILerArquivo extends Remote {
  public boolean openFile(String filename) throws RemoteException;

  public String nextLine() throws RemoteException;

  public boolean closeFile(String filename) throws RemoteException;
}