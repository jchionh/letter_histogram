package experiments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class Experiment1 {

    private static final String letters[] = {
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j",
            "k",
            "l",
            "m",
            "n",
            "o",
            "p",
            "q",
            "r",
            "s",
            "t",
            "u",
            "v",
            "w",
            "x",
            "y",
            "z",
    };

    public static void experiment(String[] args) {
	    // write your code here
        //String filePath = "data/words/text.txt";
        String filePath = "data/words/sowpods_3_5.txt";

        URL path = ClassLoader.getSystemResource(filePath);

        // now let's open the file for reading
        if(path == null) {
            System.out.println("File does not exist: " + filePath);
        } else {
            System.out.println("url: " + path.toString());
            try {

                HashSet<String> seenWords = new HashSet<String>();
                Map<String, Float> globalFreqHistogram = new HashMap<String, Float>();
                Map<String, Float> globalCountHistogram = new HashMap<String, Float>();

                // a reusable field we will use for reflection in case 4
                Field field = String.class.getDeclaredField("value");
                field.setAccessible(true);

                Scanner scanner = new Scanner(path.openStream());

                // from the scanner stream, we want to pick out words
                // these are delimited with non alpha letters
                scanner.useDelimiter("[^a-zA-Z]");

                // this is our word count
                int wordCount = 0;
                int letterCount = 0;

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
                                letterCount++;
                                //System.out.println("char: " + chars[i]);
                                String charAsString = Character.toString(chars[i]);
                                Float count = currentHistogram.get(charAsString);
                                if (count == null) {
                                    currentHistogram.put(charAsString, 1.0f);
                                    //System.out.println(charAsString + " 1.0f");
                                } else {
                                    count += 1.0f;
                                    currentHistogram.put(charAsString, count);
                                    //System.out.println(charAsString + " " + count);
                                }
                            }

                            System.out.println("(" + processedWord + ")");
                            // now compute the frequency of the letter in the word
                            // let's compute the global average frequency so far
                            Iterator entries = currentHistogram.entrySet().iterator();
                            while(entries.hasNext()) {
                                Map.Entry<String, Float> entry = (Map.Entry<String, Float>) entries.next();
                                String letter = entry.getKey();
                                Float count = entry.getValue();
                                Float localFreq = (count.floatValue() / (float) wordLength);

                                // get the global letter frequency
                                Float freq = globalFreqHistogram.get(letter);
                                if (freq == null) {
                                    globalFreqHistogram.put(letter, localFreq);
                                    globalCountHistogram.put(letter, 1.0f);
                                } else {
                                    Float computedFreq = ((freq.floatValue() * ((float) wordCount - 1.0f)) + localFreq) / (float) wordCount;
                                    globalFreqHistogram.put(letter, computedFreq);
                                    Float globalCount = globalCountHistogram.get(letter);
                                    globalCountHistogram.put(letter, globalCount + count);
                                }
                                System.out.println("\t[" + letter + "] c: " + count + " f: " + (count.floatValue() / (float) wordLength));
                            }


                        }
                        //System.out.println("word: " + processedWord + "[" + processedWord.length() + "] - " + (unique ? "unique" : "duplicated"));
                    }

                }

                // now let's print out letter count
                //Iterator entries = globalFreqHistogram.entrySet().iterator();
                Iterator entries = globalCountHistogram.entrySet().iterator();

                //float divisor = (float) letterCount / 26.0f;
                float total = 0.0f;

                while(entries.hasNext()) {
                    Map.Entry<String, Float> entry = (Map.Entry<String, Float>) entries.next();
                    String letter = entry.getKey();
                    Float count = entry.getValue();
                    float normz = count.floatValue() / (float)letterCount;
                    total += normz;
                    System.out.println("[" + letter + "] c: " + count + " f: " + normz);
                }
                System.out.println("Letter Count: " + letterCount + " total n: " + total);

                float cumulate = 0.0f;

                // go thru every letter and build a cumulative histogram
                for (int i = 0; i < letters.length; ++i) {
                    Float count = globalCountHistogram.get(letters[i]);
                    if (count == null) {
                        count = 0.0f;
                    }
                    cumulate += count.floatValue() / (float)letterCount;
                    System.out.println("[" + letters[i] + "] cumulative: " + cumulate);

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
