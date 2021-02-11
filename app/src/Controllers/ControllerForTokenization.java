package Controllers;

import Classes.User;
import Interfaces.CardTokenServerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

public class ControllerForTokenization {
    private User currUser;
    private CardTokenServerInterface tokenizerServer;
    //Input format for card number
    //The card number must be 16 digit number
    //The first digit must be 3,4,5 or 6
    private String regexForCardNum = "^[3456]\\d{15}$";
    //Input format for token
    //The token must be 16 digit number too
    private String regexForToken = "^\\d{16}$";

    @FXML
    private TextField TxtCardNum;

    @FXML
    private TextField TxtToken;

    @FXML
    private Button BttnToken;

    @FXML
    private Button BttnCardNum;

    public void setUser(User user) {
        currUser = user;
    }

    public void setServer(CardTokenServerInterface server) {
        tokenizerServer = server;
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
    void bttnCardNumPressed(ActionEvent event) throws RemoteException {
        if(currUser.canGetCardNumFromToken()) {
            if(TxtToken.getText().equals("")) {
                showErrorAsAlert("You have to insert token!");
            } else {
                if (!TxtToken.getText().matches(regexForToken)) {
                    showErrorAsAlert("Invalid token!");
                } else {
                    String cardNumber = tokenizerServer.getCardNumFromToken(TxtToken.getText());
                    if (cardNumber == null) {
                        showErrorAsAlert("Unregistered token!");
                    } else {
                        TxtCardNum.setText(cardNumber);
                    }
                }
            }
        } else {
            showErrorAsAlert("You don't have permission to get the card number!");
        }
    }

    @FXML
    void bttnTokenPressed(ActionEvent event) throws RemoteException {
        if(currUser.canRegisterToken()) {
            if(TxtCardNum.getText().equals("")) {
                showErrorAsAlert("You have to insert card number!");
            } else {
                if(!TxtCardNum.getText().matches(regexForCardNum)) {
                    showErrorAsAlert("Invalid card number!");
                } else {
                    String token = tokenizerServer.registerToken(TxtCardNum.getText());
                    if (token == null) {
                        showErrorAsAlert("Invalid card number!");
                    } else {
                        TxtToken.setText(token);
                    }
                }
            }
        } else {
            showErrorAsAlert("You don't have permission to tokenize card number!");
        }
    }
}
