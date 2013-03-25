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
    private static final String alphabets[] = {
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z",
    };

    private static final String vowels[] = { "A", "E", "I", "O", "U" };

    /**
     * gets a reference to the static alphabet array
     * @return
     */
    public static final String[] getAlphabets() {
        return alphabets;
    }

    /**
     * get the reference to vowels array
     * @return
     */
    public static final String[] getVowels() {
        return vowels;
    }
}
