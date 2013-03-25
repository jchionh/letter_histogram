package letter_histogram;

import letter_histogram.ds.AlphabetGenerator;
import letter_histogram.ds.FrequencyHistogram;

import java.net.URL;

/**
 * User: jchionh
 * Date: 3/24/13
 * Time: 9:07 PM
 */
public class Main {
    private static final String inputTextFilePath = "data/words/sowpods_3_5.txt";
    private static final int NUM_ROWS = 2;
    private static final int NUM_COLUMNS = 4;
    private static final int NUM_CHARACTERS = NUM_ROWS * NUM_COLUMNS;
    private static final float PERCENT_VOWELS = 0.6f;

    public static void main(String[] args) {
        System.out.println("Processing: " + inputTextFilePath);

        URL path = ClassLoader.getSystemResource(inputTextFilePath);

        // now let's open the file for reading
        if(path == null) {
            System.out.println("File does not exist: " + inputTextFilePath);
            return;
        } else {
            System.out.println("url: " + path.toString());
        }

        // create our frequency histogram
        FrequencyHistogram freqHistogram = new FrequencyHistogram(path);
        // build it!
        freqHistogram.buildHistogram();
        //freqHistogram.printCumulativeHistogram();

        AlphabetGenerator alphaGenerator = new AlphabetGenerator(NUM_CHARACTERS, PERCENT_VOWELS);
        alphaGenerator.setHistogram(freqHistogram);

        for (int times = 0; times < 1; ++times) {
            String characters[] = alphaGenerator.generateCharacters();
            System.out.println("---------------");
            for (int y = 0; y < NUM_ROWS; ++y) {
                for (int x = 0; x < NUM_COLUMNS; ++x) {
                    System.out.print(characters[(y * NUM_COLUMNS) + x] + "  ");
                }
                System.out.println("");
            }
        }


    }
}
