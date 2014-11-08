package theschoolproject;

import java.awt.Desktop;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class UsefulSnippets {

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
        Returns an Integer array of Values between the values in the range starting at 
        1 and going to range - 1. The Integer array's length is the amount of unique numbers you want
    */
    public static int[] generateUniqueRandomNumber(int range, int amountOfNumbers){
        if(range < amountOfNumbers){
            return null;
        }
        int[] output = new int[amountOfNumbers]; 
        ArrayList<Integer> al = new ArrayList();
        
        for(int i = 0; i < range; i++){
            al.add(i);
        }
        
        Collections.shuffle(al, new Random());
        
        for(int j = 0; j < output.length; j++){
            output[j] = al.get(j);
        }
        
        return output;
    }
}
