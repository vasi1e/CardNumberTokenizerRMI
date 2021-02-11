package Implementations;

import Classes.PairOfCardNumberAndToken;
import Classes.User;
import Interfaces.CardTokenServerInterface;
import Interfaces.DBConnectionInterface;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public final class CardTokenServer extends UnicastRemoteObject implements CardTokenServerInterface {
    private DBConnectionInterface db;
    private boolean isDBConnected = false;

    public CardTokenServer() throws RemoteException {
        //if we have initialize the database and the users already
        //we don't need to do it again
        if (!isDBConnected) {
            db = new DB();
            isDBConnected = true;

            //Initialize/Save 2 users for examples
            this.initializeUsers();
        }
    }

    protected void initializeUsers() {
        User user1 = new User("gosho", "1678", true, true);
        db.saveUser(user1);

        User user2 = new User("vasi1e", "123", false, false);
        db.saveUser(user2);
    }

    @Override
    public User getUser(String name, String pass) throws RemoteException {
        return db.getUser(name, pass);
    }

    //Check if card number is valid using Luhn formula
    private boolean isValidNumberVieLuhn(char[] cardDigits) {
        int nSum = 0;
        boolean isSecond = false;
        for (int i = 15; i >= 0; i--) {
            int d = cardDigits[i] - '0';

            //Multiply only even positions
            if (isSecond) {
                d = d * 2;
            }

            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }

        return (nSum % 10 == 0);
    }

    //Save the pair of card number and token in the DB
    //and return the token for visualization
    @Override
    public String registerToken(String cardNumber) throws RemoteException {
        char[] cardDigits = cardNumber.toCharArray();

        if(!isValidNumberVieLuhn(cardDigits)) {
            return null;
        }

        Random rand = new Random();
        char[] token = new char[16];
        int sumOfD, currNum;
        do {
            sumOfD = 0;

            //The first digit cannot be 3, 4, 5 or 6
            do {
                currNum = rand.nextInt(10);
            } while (currNum >= 3 && currNum<= 6);

            token[0] = (char) ('0' + currNum);
            sumOfD += currNum;

            for(int i = 1; i < 16; i++) {
                //The last 4 digits stay the same
                if(i > 11) {
                    token[i] = cardDigits[i];
                    sumOfD += cardDigits[i] - '0';
                } else {
                    //The other digits must be different from the
                    //card number in the same position
                    do {
                        currNum = rand.nextInt(10);
                        token[i] = (char) ('0' + currNum);
                    } while (token[i] == cardDigits[i]);

                    sumOfD += currNum;
                }
            }
        //Generate different tokens until the token is valid and unregistered yet
        } while (sumOfD % 10 == 0 && getCardNumFromToken(cardDigits.toString()) != null);

        String result = String.valueOf(token);
        //Save to DB
        db.savePairOfCardNumAndToken(new PairOfCardNumberAndToken(cardNumber, result));
        return result;
    }

    @Override
    public String getCardNumFromToken(String token) throws RemoteException {
        return db.getCardNumberByToken(token).getCardNumber();
    }
}