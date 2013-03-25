package letter_histogram.ds;

/**
 * User: jchionh
 * Date: 3/24/13
 * Time: 10:54 PM
 */
public class AlphabetGenerator {

    private static final int NO_EXCLUDE = -1;

    FrequencyHistogram frequencyHistogram;
    int numCharcters = 0;
    int maxVowels = 0;
    float vowelRatio = 0.0f;

    /**
     * ctor for our Alphabet Generator
     * @param numCharacters how many number of characters to generate?
     * @param vowelRatio    what is the vowel ratio? 0.0 - 1.0
     */
    public AlphabetGenerator(int numCharacters, float vowelRatio) {
        this.numCharcters = numCharacters;
        this.vowelRatio = vowelRatio;
        maxVowels = (int) (vowelRatio * (float)numCharacters);
    }

    /**
     * set our frequency histogram so we can generate our alphabets
     * @param frequencyHistogram
     */
    public void setHistogram(FrequencyHistogram frequencyHistogram) {
        this.frequencyHistogram = frequencyHistogram;
    }

    /**
     * here we genrate our characters based on ctor parameters
     * @return
     */
    public String[] generateCharacters() {
        String characters[] = new String[numCharcters];
        // first we generate our vowels
        int numVowelsToGenerate = (int) Math.ceil(Math.random() * (double) maxVowels);
        for (int i = 0; i < numVowelsToGenerate; ++i) {
            characters[i] = frequencyHistogram.getRandomVowel();
        }
        // then generate our non vowels
        for(int i = numVowelsToGenerate; i < numCharcters; ++i) {
            characters[i] = frequencyHistogram.getRandomNonVowel();
        }

        // now let's shuffle our array!
        // between n and 2n times
        int timesToShuffle = characters.length + (int) Math.ceil(Math.random() * (double) characters.length);
        randomShuffle(characters, timesToShuffle);
        return characters;
    }

    /**
     * shuffle a given array n times
     * @param characters
     * @param times
     */
    private static void randomShuffle(String characters[], int times) {
        for (int n = 0; n < times; ++n) {
            int firstPos = getRandomPosition(characters.length);
            int secondPos = getRandomPosition(characters.length, firstPos);
            swapPositions(characters, firstPos, secondPos);
        }
    }

    /**
     * private method to swapPositions character positions in string
     * @param charaters
     * @param a
     * @param b
     */
    private static void swapPositions(String charaters[], int a, int b) {
        String temp = charaters[a];
        charaters[a] = charaters[b];
        charaters[b] = temp;
    }

    /**
     * get any random position within numElements
     * @param numElements
     * @return
     */
    private static int getRandomPosition(int numElements) {
        return getRandomPosition(numElements, NO_EXCLUDE);
    }

    /**
     * get a random position less than numElements, and exclude exclude number
     * @param numElements
     * @param exclude
     * @return
     */
    private static int getRandomPosition(int numElements, int exclude) {
        while(true) {
            int position = (int) Math.floor(Math.random() * (double) numElements);
            if (position != exclude) {
                return position;
            }
        }
    }
}
