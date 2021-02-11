package Implementations;

import Classes.PairOfCardNumberAndToken;
import Classes.User;
import Interfaces.DBConnectionInterface;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//This implementation of database uses XML files as database
public class DB implements DBConnectionInterface {
    //Declare the path to the project folder
    private String pathToProjectFolderWithXMLFiles = "";
    private XStream xstream = new XStream(new DomDriver());

    private <T> void writeInXMLFile(String name, T obj) {
        FileOutputStream fos = null;

        try{
            File f = new File(pathToProjectFolderWithXMLFiles + name);
            List<T> list;

            //Check if we need new list or we already have some elements
            if(f.exists()) {
                list = (ArrayList<T>) getListFromXMLFile(name);
            } else {
                list = new ArrayList<T>();
            }

            list.add(obj);
            String xml = xstream.toXML(list);
            byte[] bytes = xml.getBytes("UTF-8");
            fos = new FileOutputStream(pathToProjectFolderWithXMLFiles + name,false);
            //XStream doesn't do that for us
            fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
            fos.write(bytes);

        }catch (Exception e){
            System.err.println("Error in XML Write: " + e.getMessage());
        }
        finally{
            if(fos != null){
                try{
                    fos.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ArrayList getListFromXMLFile(String name) {
        File xmlFile = new File(pathToProjectFolderWithXMLFiles + name);
        return (ArrayList) xstream.fromXML(xmlFile);
    }

    @Override
    public User getUser(String name, String pass) {
        List<User> allUsers = getListOfAllUsers();
        if(allUsers.stream().
                anyMatch(user -> user.getNickname().equals(name) && user.getPassword().equals(pass))) {

            return allUsers.stream().
                    filter(user -> user.getNickname().equals(name)).findFirst().get();
        }
        else {

            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        writeInXMLFile("Users.xml", user);
    }

    @Override
    public PairOfCardNumberAndToken getCardNumberByToken(String token) {
        List<PairOfCardNumberAndToken> allTokens = getListOfAllPairs();
        if(allTokens.stream().
                anyMatch(t -> t.getToken().equals(token))) {

            return allTokens.stream().
                    filter(t -> t.getToken().equals(token)).findFirst().get();
        } else {
            return null;
        }
    }

    @Override
    public void savePairOfCardNumAndToken(PairOfCardNumberAndToken pct) {
        writeInXMLFile("Tokens.xml", pct);
    }

    @Override
    public ArrayList<User> getListOfAllUsers() {
        return (ArrayList<User>) getListFromXMLFile("Users.xml");
    }

    @Override
    public ArrayList<PairOfCardNumberAndToken> getListOfAllPairs() {
        return (ArrayList<PairOfCardNumberAndToken>) getListFromXMLFile("Tokens.xml");
    }
}
