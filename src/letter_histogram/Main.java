package letter_histogram;

import letter_histogram.ds.FrequencyHistogram;

import java.net.URL;

/**
 * User: jchionh
 * Date: 3/24/13
 * Time: 9:07 PM
 */
public class Main {
    private static final String inputTextFilePath = "data/words/sowpods_3_5.txt";

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
        // now print the count!
        freqHistogram.printFrequencyHistogram();
    }
}
