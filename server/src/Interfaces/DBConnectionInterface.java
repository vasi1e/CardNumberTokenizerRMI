package Interfaces;

import Classes.PairOfCardNumberAndToken;
import Classes.User;

import java.util.ArrayList;

public interface DBConnectionInterface {
    public User getUser(String name, String pass);
    public void saveUser(User user);
    public PairOfCardNumberAndToken getCardNumberByToken(String token);
    public void savePairOfCardNumAndToken(PairOfCardNumberAndToken pct);
    public ArrayList<User> getListOfAllUsers();
    public ArrayList<PairOfCardNumberAndToken> getListOfAllPairs();
}
