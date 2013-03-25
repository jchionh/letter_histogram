package letter_histogram.ds;

import letter_histogram.constants.Alphabets;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * User: jchionh
 * Date: 3/24/13
 * Time: 9:21 PM
 */
public class FrequencyHistogram {

    private static final String TAG = FrequencyHistogram.class.getSimpleName();

    private URL urlToWords;
    private Map<String, Integer> freqHistogram = new HashMap<String, Integer>();
    private Map<String, Float> cHistogram = new HashMap<String, Float>();
    private int totalLetters = 0;

    // ctor
    /**
     *
     * @param urlToWords
     */
    public FrequencyHistogram(URL urlToWords) {
        // check for null
        if (urlToWords == null) {
            throw new NullPointerException("[FrequencyHistogram] must init with a non null URL");
        }
        this.urlToWords = urlToWords;
    }

    /**
     * just print it!
     */
    public void printFrequencyHistogram() {
        String alphabets[] = Alphabets.getAlphabets();
        for (int i = 0; i < alphabets.length; ++i) {
            Integer count = freqHistogram.get(alphabets[i]);
            int realCount = 0;
            if (count != null) {
                realCount = count.intValue();
            }
            System.out.println("[" + alphabets[i] + "] c: " + realCount);
        }
        System.out.println("Total letters: " + totalLetters);
    }

    /**
     * debug print our cumulative histogram
     */
    public void printCumulativeHistogram() {
        String alphabets[] = Alphabets.getAlphabets();
        for (int i = 0; i < alphabets.length; ++i) {
            Float value = cHistogram.get(alphabets[i]);
            float realValue = 0.0f;
            if (value != null) {
                realValue = value.floatValue();
            }
            System.out.println("[" + alphabets[i] + "] c: " + realValue);
        }
        System.out.println("Total letters: " + totalLetters);
    }

    /**
     * here we get a random alphabet
     * @return
     */
    public String getRandomAplhabet() {
        float randomNumber = (float) Math.random();
        String alphabets[] = Alphabets.getAlphabets();
        for (int i = 0; i < alphabets.length; ++i) {
            Float value = cHistogram.get(alphabets[i]);
            float realValue = 0.0f;
            if (value != null) {
                realValue = value.floatValue();
            }
            if (randomNumber < realValue) {
                //System.out.println("rv: " + realValue + " rn: " + randomNumber);
                return alphabets[i];
            }
        }
        return "*";
    }

    /**
     * here we get a random vowel
     * @return
     */
    public String getRandomVowel() {
        return getRandomAlphabet(true);
    }

    /**
     * get a non vowel
     * @return
     */
    public String getRandomNonVowel() {
        return getRandomAlphabet(false);
    }

    public String getRandomAlphabet(boolean isVowel) {
        List vowelsList = Arrays.asList(Alphabets.getVowels());
        int tries = 0;
        while(true) {
            tries++;
            String character = getRandomAplhabet();
            if (vowelsList.contains(character) == isVowel) {
                //System.out.println("alphabet generation tries: " + tries);
                return character;
            }
        }
    }

    /**
     * build the cumulative histogram for generating alphabets
     */
    private void buildCumulativeHistogram() {

        // don't need to do anything
        if (totalLetters <= 0) {
            return;
        }

        float cumulate = 0.0f;

        String alphabets[] = Alphabets.getAlphabets();
        for (int i = 0; i < alphabets.length; ++i) {
            Integer count = freqHistogram.get(alphabets[i]);
            int realCount = 0;
            if (count != null) {
                realCount = count.intValue();
            }
            float normalizedFreq = ((float) realCount / (float) totalLetters);
            // cumulate the freq after normlaization
            cumulate += normalizedFreq;
            cHistogram.put(alphabets[i], Float.valueOf(cumulate));
        }
    }

    /**
     * now we can build the frequency histogram
     */
    public void buildHistogram() {

        // create a scanner to start scanning the stream
        Scanner scanner = null;
        try {
            // we record our seen words
            HashSet<String> seenWords = new HashSet<String>();

            // a reusable field we will use for reflection in case 4
            Field field = String.class.getDeclaredField("value");
            field.setAccessible(true);

            scanner = new Scanner(urlToWords.openStream());
            // from the scanner stream, we want to pick out words
            // these are delimited with non alpha letters
            scanner.useDelimiter("[^a-zA-Z]");

            // total letters we've seen
            totalLetters = 0;

            // iterate for every word that we pick up
            while (scanner.hasNext()) {
                String word = scanner.next().toUpperCase();
                if (word.length() > 0) {
                    // now we record our string into the hashset
                    // and only count the letter freqs if it's unique
                    boolean unique = seenWords.add(word);

                    if (unique) {
                        // fields to extract characters in the word
                        final char[] chars = (char[]) field.get(word);
                        final int wordLength = chars.length;

                        for (int i = 0; i < wordLength; ++i) {
                            totalLetters++;
                            String charAsString = Character.toString(chars[i]);
                            Integer count = freqHistogram.get(charAsString);
                            if (count == null) {
                                freqHistogram.put(charAsString, Integer.valueOf(1));
                            } else {
                                int total = count.intValue() + 1;
                                freqHistogram.put(charAsString, Integer.valueOf(total));
                            }
                        }

                    }
                }
            }

            // once we finished building the freq histogram,
            // let's build the cumulative
            buildCumulativeHistogram();

        } catch (IOException e) {
            System.out.println("[" + TAG + "] " + "Cannot open stream at " + urlToWords.toString());
            System.out.println(e);
        } catch (NoSuchFieldException e) {
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
