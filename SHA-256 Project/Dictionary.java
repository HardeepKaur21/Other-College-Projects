import java.io.*;
import java.util.*;

public class Dictionary {
    public static List<String> getWords(){
        List<String> words = new ArrayList<>();
        String filePath = "C:\\Users\\gillh\\OneDrive\\Documents\\Java Projects\\SHA-256 Problem\\words.txt";

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public static List<String> getSubjects() {
        return Arrays.asList(
            "The cat", "A dog", "The bird", "The girl", "The boy",
            "The chef", "My friend", "The teacher", "The scientist", 
            "The athlete", "The artist", "The musician", "The pilot", 
            "The astronaut", "The baby", "My neighbor", "The student", 
            "The doctor", "The gardener", "The magician", "The engineer"
        );
    }
    
    public static List<String> getVerbs() {
        return Arrays.asList(
            "eats", "chases", "sees", "likes", "jumps over", 
            "builds", "writes", "paints", "cooks", "drives", 
            "teaches", "learns", "studies", "flies", "explores", 
            "throws", "catches", "notices", "fixes", "admires"
        );
    }
    
    public static List<String> getObjects() {
        return Arrays.asList(
            "a ball", "a mouse", "a tree", "a car", "a book", 
            "the sky", "the stars", "a painting", "a house", 
            "a garden", "a piano", "a spaceship", "a computer", 
            "a recipe", "using a map", "a telescope", "a robot", 
            "a kite", "a lamp", "a masterpiece"
        );
    }
}
