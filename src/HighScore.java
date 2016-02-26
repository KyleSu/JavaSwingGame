import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HighScore {
    public String fileName = "HighScores.txt";
    String line = null;

    public HighScore() {

    }
    
    public void newScore(int score) {
        int[] scores = new int[10];


    try {
        // FileReader reads text files in the default encoding.
        FileReader fileReader = new FileReader(fileName);

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        for (int i = 0; i < 10; i++) {
            if ((line = bufferedReader.readLine()) != null) {
                scores[i] = Integer.parseInt(line);
            } else {
                scores[i] = 0;
            }
        }

        // Always close files.
        bufferedReader.close();            
    }
    catch(FileNotFoundException ex) {
        System.out.println(
                "Unable to open file '" + 
                        fileName + "'");                
    }
    catch(IOException ex) {
        System.out.println(
                "Error reading file '" 
                        + fileName + "'");                   
    }
    
    boolean newScore = false;
    for (int i = 0; i < 10; i ++) {
        if (score >= scores[i]) {
            newScore = true;
            if (i == 9) {
                scores[i] = score;
            } else {
                int j = 8;
                while (j >= i) {
                    scores[j] = scores[j + 1];
                }
                scores[i] = score;
            }
        }
    }
    
    if (newScore) {
        System.out.println("New scores on the high scores list of " + score);
        System.out.println("High Scores");
    }
    for (int i = 0; i < 10; i ++) {
        System.out.println(scores[i] + " ");
    }
    }
}

