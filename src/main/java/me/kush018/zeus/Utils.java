package me.kush018.zeus;

import java.util.ArrayList;

public class Utils {
    public static String[] divideIntoArgs(String s) {
        //an arraylist of all the arguments
        ArrayList<String> contentArr = new ArrayList<>();
        //if quotation marks have been found
        boolean foundQuotationMarks = false;
        //if a new argument has "started" while traversing through the array
        boolean newWord = true;
        //for each character in the String s, with the index of the character being i,
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                //if the current character is a space
                if (!foundQuotationMarks) {
                    //if quotation marks were not found, we must start a new argument
                    newWord = true;
                } else {
                    //if quotation marks were found, then we must consider the space character to be part of the argument
                    if (newWord) {
                        //if a new argument was just started, we must add the current character as a String to the contentArr
                        contentArr.add(Character.toString(s.charAt(i)));
                        newWord = false;
                    } else {
                        //if a new argument as not just been started and we are in the middle of an argument
                        //we must add the current character to the last element in contentArr
                        contentArr.set(contentArr.size() - 1, contentArr.get(contentArr.size() - 1) + s.charAt(i));
                    }
                }
            } else if (s.charAt(i) == '"') {
                //if the current character is a double quote
                if (!foundQuotationMarks) {
                    //if quotation marks were not already found, it means that a new argument is starting
                    foundQuotationMarks = true;
                } else {
                    //if quotation marks were already found the double quotes mark the end of the argument
                    foundQuotationMarks = false;
                    //the quotation marks also start a new argument
                    newWord = true;
                }
            } else {
                //if its a normal character
                if (newWord) {
                    //if a new argument just started
                    contentArr.add(Character.toString(s.charAt(i)));
                    newWord = false;
                } else {
                    contentArr.set(contentArr.size() - 1, contentArr.get(contentArr.size() - 1) + s.charAt(i));
                }
            }
        }
        //we need to convert the ArrayList contentArr to a plain one dimensional String array
        //we create an empty String array with the same length as contentArr
        String[] argvArr = new String[contentArr.size()];
        //for each element in contentArr
        for (int i = 0; i < contentArr.size(); i++) {
            //we sent the element in the plain String array to the contentArr
            argvArr[i] = contentArr.get(i);
        }
        //finally, we can return argvArr
        return argvArr;
    }
}
