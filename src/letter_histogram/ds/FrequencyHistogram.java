package letter_histogram.ds;

import letter_histogram.constants.Alphabets;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;

/**
 * User: jchionh
 * Date: 3/24/13
 * Time: 9:21 PM
 */
public class FrequencyHistogram {

    private static final String TAG = FrequencyHistogram.class.getSimpleName();

    private URL urlToWords;
    //private Map<String, Integer> freqHistogram = new HashMap<String, Integer>();
    //private Map<String, Double> cHistogram = new HashMap<String, Double>();

    private final int fHistogram[] = new int[Alphabets.NUM_ALPHABETS];
    private final double cHistogram[] = new double[Alphabets.NUM_ALPHABETS];

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
        initfHistogram();
        initcHistogram();
    }

    /**
     * just print it!
     */
    public void printFrequencyHistogram() {
        System.out.println("-- Frequency Histogram --");
        for (int i = 0; i < Alphabets.NUM_ALPHABETS; ++i) {
            int count = fHistogram[i];
            System.out.println("[" + Character.toString((char) (i + Alphabets.CHAR_CODE_A)) + "] " + count);
        }
        System.out.println("Total letters: " + totalLetters);
    }

    /**
     * debug print our cumulative histogram
     */
    public void printCumulativeHistogram() {
        System.out.println("-- Cumulative Histogram --");
        for (int i = 0; i < Alphabets.NUM_ALPHABETS; ++i) {
            double value = cHistogram[i];
            System.out.println("[" + Character.toString((char) (i + Alphabets.CHAR_CODE_A)) + "] " + value);
        }
        System.out.println("Total letters: " + totalLetters);
    }

    /**
     * here we get a random alphabet
     * @return
     */
    public char getRandomAlphabet() {
        double randomNumber = Math.random();
        for (int i = 0; i < Alphabets.NUM_ALPHABETS; ++i) {
            double value = cHistogram[i];
            if (randomNumber < value) {
                return (char) (i + Alphabets.CHAR_CODE_A);
            }
        }
        return '*';
    }

    /**
     * here we get a random vowel
     * @return
     */
    public char getRandomVowel() {
        return getRandomAlphabet(true);
    }

    /**
     * get a non vowel
     * @return
     */
    public char getRandomNonVowel() {
        return getRandomAlphabet(false);
    }

    /**
     * we get a random alphabet, and we can say if it's
     * a vowel or not
     * @param isVowel
     * @return
     */
    public char getRandomAlphabet(boolean isVowel) {
        int tries = 0;
        while(true) {
            tries++;
            char character = getRandomAlphabet();
            if (Alphabets.isVowel(character) == isVowel) {
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

        double cumulate = 0.0;
        for (int i = 0; i < Alphabets.NUM_ALPHABETS; ++i) {
            int count = fHistogram[i];
            double normalizedFreq = ((double) count / (double) totalLetters);
            // cumulate the freq after normlaization
            cumulate += normalizedFreq;
            cHistogram[i] = cumulate;
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

            scanner = new Scanner(urlToWords.openStream());
            // from the scanner stream, we want to pick out words
            // these are delimited with non alpha letters
            scanner.useDelimiter("[^a-zA-Z]");

            // total letters we've seen
            totalLetters = 0;

            // iterate for every word that we pick up
            while (scanner.hasNext()) {
                String word = scanner.next().toUpperCase();
                int wordLength = word.length();
                if (wordLength > 0) {
                    // now we record our string into the hashset
                    // and only count the letter freqs if it's unique
                    boolean unique = seenWords.add(word);

                    if (unique) {
                        for (int i = 0; i < wordLength; ++i) {
                            totalLetters++;
                            char character = word.charAt(i);
                            int charCode = (int) character;
                            int index = charCode - Alphabets.CHAR_CODE_A;
                            // increment our freq count
                            fHistogram[index]++;
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
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * init the fHistogram to 0
     */
    private void initfHistogram() {
        for (int i = 0; i < Alphabets.NUM_ALPHABETS; ++i) {
            fHistogram[i] = 0;
        }
    }

    /**
     * init the cHistogram to 0.0
     */
    private void initcHistogram() {
        for (int i = 0; i < Alphabets.NUM_ALPHABETS; ++i) {
            cHistogram[i] = 0.0;
        }
    }
}
