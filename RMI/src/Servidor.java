import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor implements ILerArquivo {
  BufferedReader mrf;

  public static void main(String[] args) {
    try {
      Servidor server = new Servidor();
      ILerArquivo ler = (ILerArquivo) UnicastRemoteObject.exportObject(server, 0);
      LocateRegistry.createRegistry(7777);
      Registry registro = LocateRegistry.getRegistry(7777);
      registro.rebind("ILerArquivo", ler);
    } catch (Exception e) {
      System.err.println("Erro no servidor: " + e.toString());
      e.printStackTrace();
    }
  }

  @Override
  public boolean openFile(String filename) {
    File myFile = new File(filename);

    try {
      if (myFile.canRead() && myFile.isFile()) {
        mrf = new BufferedReader(new FileReader(myFile));
        return true;
      }
    } catch (Exception e) {
      // Em caso de exceção, é necessário manipulá-la
      // e o servidor continua
    }

    return false;
  }

  @Override
  public String nextLine() throws RemoteException {
    try {
      String s = mrf.readLine();
      return s;
    } catch (IOException e) {
      System.out.println("Vixe, deu erro: " + e.toString());
    }
    return null;
  }

  @Override
  public boolean closeFile(String filename) throws RemoteException {
    if (mrf != null) {
      try {
        mrf.close();
        return true;
      } catch (IOException e) {
        System.out.println("Vixe, deu erro: " + e.toString());
        return false;
      }
    }

    return false;
  }
}
