package main;

import Implementations.CardTokenServer;
import Interfaces.CardTokenServerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegisterServer {
    public static void main(String[] args)   {
        try {
            CardTokenServerInterface server = new CardTokenServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("CardTokenServer", server);
            System.out.println("Remote object named: CardTokenServer is registered. " +
                    "Registry is running.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
