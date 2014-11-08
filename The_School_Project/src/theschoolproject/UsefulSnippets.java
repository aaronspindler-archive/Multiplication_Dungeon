package theschoolproject;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class UsefulSnippets {

    /*
        Opens a webpage (Commented since Aaron doesn't know how to.)
    */
    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
        Generates a boring number that is in the given range starting at 
        1 and going to range - 1.  
    */
    public static int generateRandomNumber(int range){
        return (int)(Math.random() * range);
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
    
    public static BufferedImage loadImage(String FilePath){
        BufferedImage img = null;
        try {
            img = ImageIO.read(UsefulSnippets.class.getResource(FilePath));
        } catch (IOException ex) {
            Logger.getLogger(UsefulSnippets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }
}
