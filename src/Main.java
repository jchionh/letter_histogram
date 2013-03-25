import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        String filePath = "data/words/text.txt";
        URL path = ClassLoader.getSystemResource(filePath);

        // now let's open the file for reading
        if(path == null) {
            System.out.println("File does not exist: " + filePath);
        } else {
            System.out.println("url: " + path.toString());
            try {

                HashSet<String> seenWords = new HashSet<String>();
                Map<String, Float> histogram = new HashMap<String, Float>();

                // a reusable field we will use for reflection in case 4
                Field field = String.class.getDeclaredField("value");
                field.setAccessible(true);

                Scanner scanner = new Scanner(path.openStream());

                // from the scanner stream, we want to pick out words
                // these are delimited with non alpha letters
                scanner.useDelimiter("[^a-zA-Z]");

                // this is our word count
                int wordCount = 0;

                // iterate for every word that we pick up
                while (scanner.hasNext()) {
                    String word = scanner.next();
                    String processedWord = word.toLowerCase();
                    if (processedWord.length() > 0) {
                        // now we record our string into the hashset
                        // and only count the letter freqs if it's unique
                        boolean unique = seenWords.add(processedWord);

                        if (unique) {
                            // increment word count
                            wordCount++;

                            // fields to extract characters in the word
                            final char[] chars = (char[]) field.get(processedWord);
                            final int wordLength = chars.length;

                            // current histogram in word
                            Map<String, Float> currentHistogram = new HashMap<String, Float>();

                            //System.out.println("process: " + processedWord + " [" + wordLength + "]");

                            for (int i = 0; i < wordLength; ++i) {
                                //System.out.println("char: " + chars[i]);
                                String charAsString = Character.toString(chars[i]);
                                Float count = histogram.get(charAsString);
                                if (count == null) {
                                    currentHistogram.put(charAsString, 1.0f);
                                    //System.out.println(charAsString + " 1.0f");
                                } else {
                                    count += 1.0f;
                                    currentHistogram.put(charAsString, count);
                                    //System.out.println(charAsString + " " + count);
                                }
                            }

                            // now compute the frequency of the letter in the word
                            // now let's print out letter count
                            Iterator entries = currentHistogram.entrySet().iterator();
                            while(entries.hasNext()) {
                                Map.Entry<String, Float> entry = (Map.Entry<String, Float>) entries.next();
                                String letter = entry.getKey();
                                Float count = entry.getValue();
                                System.out.println("[" + letter + "]: " + count + " " + (count.floatValue() / (float) wordLength));
                            }


                        }
                        //System.out.println("word: " + processedWord + "[" + processedWord.length() + "] - " + (unique ? "unique" : "duplicated"));
                    }

                }

                // now let's print out letter count
                Iterator entries = histogram.entrySet().iterator();
                while(entries.hasNext()) {
                    Map.Entry<String, Float> entry = (Map.Entry<String, Float>) entries.next();
                    String letter = entry.getKey();
                    Float count = entry.getValue();
                    System.out.println("[" + letter + "]: " + count);

                }

            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            } catch (NoSuchFieldException e) {
                System.out.println(e);
            } catch (IllegalAccessException e) {
                System.out.println(e);
            }
        }








    }
}
