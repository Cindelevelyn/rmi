import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {
  public static void main(String[] args) {
    String porta = "7777";

    // if (args.length < 1) {
    // porta = null;
    // } else {
    // porta = args[0];
    // }

    try {
      Registry registro = LocateRegistry.getRegistry(Integer.parseInt(porta));
      ILerArquivo ler = (ILerArquivo) registro.lookup("ILerArquivo");

      String file;

      if (args.length < 1) {
        file = null;
      } else {
        file = args[1];
      }

      if (ler.openFile(file)) {
        String linhas;

        do {
          linhas = ler.nextLine();
          if (linhas != null) {
            System.out.println(linhas);
          } else {
            break;
          }
        } while (true);
      } else {
        System.out.println("Erro ao abrir o arquivo!");
      }
      if (ler.closeFile(file)) {
        System.out.println("Arquivo fechado com sucesso!");
      } else {
        System.out.println("Erro ao fechar o arquivo!");
      }

    } catch (Exception e) {
      System.out.println("Vixe, erro no client: " + e.toString());
      e.printStackTrace();
    }
  }
}
