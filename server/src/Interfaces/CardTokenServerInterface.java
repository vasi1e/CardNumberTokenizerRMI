package Interfaces;


import Classes.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CardTokenServerInterface extends Remote {
    public String registerToken(String cardNumber) throws RemoteException;
    public String getCardNumFromToken(String token) throws RemoteException;
    public User getUser(String name, String pass) throws RemoteException;
}
