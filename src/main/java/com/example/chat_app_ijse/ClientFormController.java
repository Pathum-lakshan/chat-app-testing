package com.example.chat_app_ijse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFormController {

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textMessage;

    final int PORT = 5000;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String message = "";

    public void initialize(){
        new Thread(() -> {
            try {
                socket = new Socket("localhost",PORT);

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (!message.equals("exit")){
                    message = dataInputStream.readUTF();
                    textArea.appendText("\n Server : " + message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void sendOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(textMessage.getText().trim());
        dataOutputStream.flush();
    }
}
