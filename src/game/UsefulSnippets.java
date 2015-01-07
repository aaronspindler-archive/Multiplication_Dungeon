package game;

import com.mysql.jdbc.Connection;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import resources.SettingsProperties;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class UsefulSnippets implements Serializable {

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int generateRandomNumber(int range) {
        return (int) (Math.random() * range);
    }

    /*
     Returns an Integer array of Values between the values in the range starting at 
     1 and going to range - 1. The Integer array's length is the amount of unique numbers you want
     */
    public static int[] generateUniqueRandomNumber(int range, int amountOfNumbers) {
        if (range < amountOfNumbers) {
            return null;
        }
        int[] output = new int[amountOfNumbers];
        ArrayList<Integer> al = new ArrayList();

        for (int i = 0; i < range; i++) {
            al.add(i);
        }

        Collections.shuffle(al, new Random());
        for (int j = 0; j < output.length; j++) {
            output[j] = al.get(j);
        }
        return output;
    }

    /*
     Loads an Image from the path specified (Lets you not have try-catch blocks in the middle of your code)
     */
    public static BufferedImage loadImage(String FilePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(UsefulSnippets.class.getResource(FilePath));
        } catch (IOException ex) {
            Logger.getLogger(UsefulSnippets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }

    public static Font loadFont(String s) {
        Font f = null;
        try {
            InputStream is = UsefulSnippets.class.getResourceAsStream(s);
            f = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException ex) {
            Logger.getLogger(UsefulSnippets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

    public static Connection makeMySQLConnection() {
        String serverIP = ("50.87.144.85");
        String port = ("3306");
        String databaseName = ("xnovax_school");
        String username = ("xnovax_school");
        String password = ("Temp123@");

        String url = String.format("jdbc:mysql://%s:%s/%s", serverIP, port, databaseName);
        System.out.println(url);
        Connection connection = null;
        try {
            connection = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public void addHighScore(String username, int score) {
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connect = makeMySQLConnection();
            statement = connect.createStatement();
            String query = String.format("Insert into (Username, Score) values ('%s','%s');", username, score);
            resultSet = statement.executeQuery(query);

            connect.close();
            statement.close();
            preparedStatement.close();
            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNumScores() {
        int numScores = 0;

        return numScores;
    }

    public String[][] getHighScoreList() {
        String[][] highScoreList = new String[getNumScores()][getNumScores()];

        return highScoreList;
    }

    public static void playMusic(String fileLoc) {
        if (SettingsProperties.programSound == true) {
            try {
                Thread sound;
                sound = new Thread() {
                    public void run() {
                        try {
                            Media mu = new Media(fileLoc);
                            MediaPlayer mediaPlayer = new MediaPlayer(mu);
                            mediaPlayer.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                sound.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
