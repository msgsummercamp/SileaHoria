package org.example;

public class MessageService {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void displayMessage() {
        System.out.println(message);
    }
}
