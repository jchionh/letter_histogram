package letter_histogram.constants;

/**
 * User: jchionh
 * Date: 3/24/13
 * Time: 9:22 PM
 */

/**
 * provides the array of alphabets via getAlphabetArray
 */
public class Alphabets {

    public static final int NUM_ALPHABETS = 26;
    public static final int CHAR_CODE_A = 65;
    public static final int CHAR_CODE_Z = 90;

    private static final char alphabets[] = {
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',
    };

    private static final char vowels[] = { 'A', 'E', 'I', 'O', 'U' };

    /**
     * gets a reference to the static alphabet array
     * @return
     */
    public static final char[] getAlphabets() {
        return alphabets;
    }

    /**
     * get the reference to vowels array
     * @return
     */
    public static final char[] getVowels() {
        return vowels;
    }

    /**
     * is alphabet given a vowel?
     * @param alphabet
     * @return
     */
    public static boolean isVowel(final char alphabet) {
        for (int i = 0; i < vowels.length; ++i) {
            if (alphabet == vowels[i]) {
                return true;
            }
        }
        return false;
    }
}
