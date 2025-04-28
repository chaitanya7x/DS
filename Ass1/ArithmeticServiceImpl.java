import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ArithmeticServiceImpl extends UnicastRemoteObject implements ArithmeticService {

    // Constructor
    public ArithmeticServiceImpl() throws RemoteException {
        super();
    }

    // Implementing the arithmetic operations

    @Override
    public double add(double a, double b) throws RemoteException {
        return a + b;
    }

    @Override
    public double subtract(double a, double b) throws RemoteException {
        return a - b;
    }

    @Override
    public double multiply(double a, double b) throws RemoteException {
        return a * b;
    }

    @Override
    public double divide(double a, double b) throws RemoteException {
        if (b == 0) {
            throw new RemoteException("Cannot divide by zero");
        }
        return a / b;
    }
}

