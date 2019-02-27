package com.company;

import com.company.ChatRooms.ChatRoomList;
import com.company.Message.Message;
import com.company.MessageSendingClasses.LogInRequestMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller implements Initializable {

    @FXML
    public TextArea inputbox, outputbox;
    public ListView userBox, channels;
    public TextField userNameBox;
    public double xOffset;
    public double yOffset;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (inputbox != null) {
            inputbox.setTextFormatter(new TextFormatter<String>(change ->
                    change.getControlNewText().length() <= 140 ? change : null));
        }

    }

    @FXML
    public void enterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendMessage(inputbox.getText());
        }
    }

    @FXML
    public void sendButtonPressed(ActionEvent event) {
        sendMessage(inputbox.getText() + "\n");
    }

    @FXML
    public void sendMessage(String message) {
        Pattern p = Pattern.compile("^\\s*");
        Matcher m = p.matcher(message);

        if (!m.matches()) {
            System.out.println(message);
            NetworkClient.get().sendObjectToServer(new Message(message, ClientProgram.get().getUser().getChannelID()));
        }
        inputbox.clear();
        inputbox.requestFocus();
    }

    @FXML
    public void recieveMessage(String message) {
        outputbox.appendText(message);
    }

    @FXML
    public void goToChatRoom() {
        String chosenRoom = channels.getSelectionModel().getSelectedItems().toString();
        chosenRoom = chosenRoom.substring(1);
        chosenRoom = chosenRoom.substring(0, chosenRoom.length() - 1);
        System.out.println(chosenRoom);
        ChatRoomList.get().getChosenChatRoom(chosenRoom);
    }

    @FXML
    public void getUserInfo(ActionEvent event) {
        Pattern p = Pattern.compile("[a-zA-Z ]{2,10}+");
        Matcher m = p.matcher(userNameBox.getText());

        if (m.matches()) {
            System.out.println("User name: " + userNameBox.getText());
            NetworkClient.get().sendObjectToServer(new LogInRequestMessage(userNameBox.getText()));
        }
    }

    @FXML
    protected void exitApp(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    protected void handleMinimize(ActionEvent event) {
        ClientGUI.primaryStage.setIconified(true);
    }
}
