package rl.screens;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {

    private MessageUtils(){

    }

    private static int getLongestMessageSize(List<String> messages){
        int longest = 0;
        for (String message : messages){
            if (message.length() > longest){
                longest = message.length();
            } //if

        } //for

        return longest;
    } //getLongestMessageSize

    private static String addPadding(String message, int maxLenght ){
        String padding = "";

        for (int i = 1; i <= (maxLenght - message.length()); i++){
            padding += " ";
        } //for
        return (char)179 + " " + message + " " + padding + (char)179;
    } //addPadding

    public static List<String> padMessages(List<String> messages){
        int maxLength = getLongestMessageSize(messages);
        List<String> paddedMessages = new ArrayList<String>();
        for (String message : messages){
            paddedMessages.add(addPadding(message, maxLength));
        }
        return paddedMessages;

    } //padMessages

    public static String createBoarder(List<String> messages){
        String boarder = "" + (char)218;
        for (int i = 2; i < getLongestMessageSize(messages); i++){
            boarder += (char)196;
        } //for
        //makes up for added spaces
        boarder += (char)191;
        return boarder;
    }



}
