import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

//faced a bug with sentences being duplicates, in order to avoid that, I decided to use hashsets for the sentences instead of lists.
//in the document need to also mention how the random sentences always give a random output and why
//line 142 is really important to placed after the for loop and explain why

public class BitcoinHashingStrategy{
    public static void main(String [] args){
        System.out.println("Press 1 for random longer sentences or 2 for shorter more grammatically correct sentences.");
        System.out.println("Enter types of sentences: ");

        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        if(num == 1){
            String[] words = loadDictionary();
            List<String> sentences = generateSentences(words);
            //find the closest pair
            String[] closestPair = findClosestHashes(sentences);

            System.out.println("\nClosest Sentences: ");
            System.out.println(closestPair[0]);
            System.out.println(closestPair[1] + "\n\n");
        }
        else if(num == 2){
            System.out.println("Enter the number of sentences you want (more than 2 ideally): ");
            int count = sc.nextInt();
            if(count > 1){
                List<String> sentences = generateGrammaticallyCorrectSentences(count);

                String[] closestPair = findClosestHashes(sentences);

                System.out.println("\nNumber of generated sentences: " + count);

                System.out.println("\nClosest Sentences: ");
                System.out.println(closestPair[0]);
                System.out.println(closestPair[1] + "\n\n");
            } else {
                System.out.println("Need more than 2 sentences please. Try again by running the code from the start.");
            }
        } else {
            System.out.println("Please run this code again to retry.");
        }

        sc.close();
        

    }

    public static String[] loadDictionary(){
        List<String> wordList = Dictionary.getWords();
        return wordList.toArray(new String[0]);
    }
    public static List<String> generateSentences(String [] words){
        Set<String> sentences = new HashSet<>();
        Random random = new Random();


        for(int i = 0; i < 1000; i++){
            StringBuilder sentence = new StringBuilder();
            int sentenceLength = 5+random.nextInt(6);  //maximum of 10 words per sentence

            for(int j = 0; j <  sentenceLength; j++){
                String word = words[random.nextInt(words.length)]; 

                //first word to be capitalised
                if(j == 0){
                    word = word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();
                } else {
                    word = word.toLowerCase();
                }

                //add word to sentence
                sentence.append(word).append(" ");
            }
            //punctuation in the end
            sentence.setCharAt(sentence.length() -1, '.');
            sentences.add(sentence.toString().trim());
        }
        return new ArrayList<>(sentences);
    }

    public static List<String> generateGrammaticallyCorrectSentences(int count){
        Set<String> sentences = new HashSet<>();
        Random rn = new Random();
        List<String> subjects = Dictionary.getSubjects();
        List<String> verbs = Dictionary.getVerbs();
        List<String> objects = Dictionary.getObjects();

        for(int i = 0; i < count; i++){
            String subject = subjects.get(rn.nextInt(subjects.size()));
            String verb = verbs.get(rn.nextInt(verbs.size()));
            String object = objects.get(rn.nextInt(objects.size()));

            String sentence = subject + " " + verb + " " + object + ".";
            sentences.add(sentence);
        }
        return new ArrayList<>(sentences);
    }

    //given code
    public static String computeSHA256(String input){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for(byte b : hash){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append(0);
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public static String[] findClosestHashes(List<String> sentences){
        String[] closestPair = new String[2];
        int maxMatches = 0;

        //this hashmap stores the hashes with their corresponding sentences
        Map<String, String> hashToSentence = new HashMap<>();

        for(String sentence : sentences) {
            //System.out.println(sentence);
            String hash = computeSHA256(sentence);

            for(String existingHash : hashToSentence.keySet()) {
                int matches = countHexMatches(hash, existingHash);

                //check for max matches
                if(matches > maxMatches) {
                    maxMatches = matches;
                    closestPair[0] = sentence;
                    closestPair[1] = hashToSentence.get(existingHash);
                    
                }
            }
            hashToSentence.put(hash, sentence); //this is important to be here 
        }
        System.out.println("\n\n - Most matches:" + maxMatches);
        return closestPair;
    }


    public static int countHexMatches(String hash1, String hash2){
        int matches = 0;

        for(int i = 0; i < hash1.length(); i++) {
            if(hash1.charAt(i) == hash2.charAt(i)){
                matches++;
            }
        }
        return matches;
    }
}