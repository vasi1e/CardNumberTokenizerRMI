package Controllers;

import Classes.User;
import Interfaces.CardTokenServerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ControllerForLogin {
    private CardTokenServerInterface tokenizerServer;

    @FXML
    private TextField TxtNickName;

    @FXML
    private PasswordField TxtPassword;

    @FXML
    private Button BttnSubmit;

    @FXML
    public void initialize() {
        initializeRMI();
    }

    //Get the server registered on these port
    protected void initializeRMI() {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            tokenizerServer = (CardTokenServerInterface) registry.lookup("CardTokenServer");
            System.out.println("Server object " + tokenizerServer + " found");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //Loads the tokenizer form
    private void openTokenizationWindow(User user, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/cardToken.fxml"));
            Parent root = (Parent)loader.load();

            //Get the controller connected to that view
            //because we need to pass the current user and the server
            ControllerForTokenization tokenizationController = loader.<ControllerForTokenization>getController();
            tokenizationController.setUser(user);
            tokenizationController.setServer(tokenizerServer);

            Stage stage = new Stage();
            stage.setTitle("Card Tokenizer");
            stage.setScene(new Scene(root, 350, 210));
            stage.show();

            //Close the loggin form
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Show errors caused by the client input
    private void showErrorAsAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void bttnSubmitPressed(ActionEvent event) throws RemoteException {
        String nickname = TxtNickName.getText();
        String password = TxtPassword.getText();
        if (nickname.equals("")) {
            showErrorAsAlert("Please insert nickname!");
        } else if(password.equals("")) {
            showErrorAsAlert("Please insert password!");
        } else {
            User user = tokenizerServer.getUser(nickname, password);
            if(user == null) {
                showErrorAsAlert("Incorrect nickname or password");
            } else {
                openTokenizationWindow(user, event);
            }
        }
    }
}
