package task3;

/**
 * Convert an octal value to its corresponding ASCII value.
 * Convert from octal(base 8)->decimal(base 10)->character.
 *
 * @author Xuan Ngo
 */
public class OctalToAscii {
    public static String octToASCII(String octStr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < octStr.length() - 2; i+=3) {
            // Convert Octal(base8) to decimal(base 10).
            Integer iOctal = Integer.parseInt(octStr.substring(i, i + 3), 8);
            // Cast decimal to its corresponding ASCII value.
            char cOctal = (char) iOctal.intValue();
            stringBuilder.append(cOctal);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        /*// Note: octal is base 8.
        String sOctal = "355";

        // Convert Octal(base8) to decimal(base 10).
        Integer iOctal = Integer.parseInt(sOctal, 8);
        System.out.println(iOctal); // Output: 237

        // Cast decimal to its corresponding ASCII value.
        char cOctal = (char) iOctal.intValue();
        System.out.println(cOctal); // Output í*/
        System.out.println(octToASCII("1411551551651561511641511571567"));
    }

}
