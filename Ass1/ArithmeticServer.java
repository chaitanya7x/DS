import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ArithmeticServer {
    public static void main(String[] args) {
        try {
            // Start the RMI registry on the default port
            Registry registry = LocateRegistry.createRegistry(2117);
            
            System.setProperty("java.rmi.server.hostname","10.255.255.254");

            // Create an instance of the ArithmeticServiceImpl
            ArithmeticServiceImpl arithmeticService = new ArithmeticServiceImpl();

            // Bind the remote arithmetic service object to the RMI registry
            Naming.rebind("ArithmeticService", arithmeticService);

            System.out.println("Arithmetic Server is ready...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

