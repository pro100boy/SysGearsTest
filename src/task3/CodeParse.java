package task3;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by User on 22.05.2017.
 */
public class CodeParse {
    // pattern for decode strings
    private static Pattern ptn = Pattern.compile("^([A-Za-z0-9]{4})[A-Za-z0-9]{0,2}([Rr](d)?(f)?(?:(?<=[df])(?:[0-9]{3})|(?:[0-9]{4}))(?:\\1)?)(?:([+-])(\\d{3}))?(\\d+)[a-zA-Z]{0,2}$");

    /**
     * Convert an octal value to its corresponding ASCII value.
     * Convert from octal(base 8)->decimal(base 10)->character.
     * @param octStr is a String value in octal format
     * @return String value in ASCII format
     */
    private static String octToASCII(String octStr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < octStr.length() - 2; i += 3) {
            // Convert Octal(base8) to decimal(base 10).
            int iOctal = Integer.parseInt(octStr.substring(i, i + 3), 8);
            // Cast decimal to its corresponding ASCII value.
            char cOctal = (char) iOctal;
            stringBuilder.append(cOctal);
        }
        return stringBuilder.toString();
    }

    /**
     * Parsing incoming coded line
     * @param codeStr coded string
     * @return required String for CSV file
     */
    public static String parse(String codeStr) {
        String[] codeParts = new String[7];
        Matcher mtr = ptn.matcher(codeStr);

        if (mtr.matches()) {
            codeParts[0] = codeStr.trim();                              // code
            codeParts[1] = mtr.group(1);                                // drive code
            codeParts[2] = mtr.group(2);                                // travel paper
            codeParts[3] = (mtr.group(3) != null) ? "true" : "false";   // danger
            codeParts[4] = (mtr.group(4) != null) ? "true" : "false";   // fragile

            if (mtr.group(5) != null)                                  // temperature
                codeParts[5] = mtr.group(5).concat(Integer.toString(Integer.parseInt(mtr.group(6))));

            codeParts[6] = octToASCII(mtr.group(7));    // name

            // build result string
            String res = Arrays.stream(codeParts).map(s -> {
                if (Objects.isNull(s)) return "";
                else return "\"" + s + "\"";
            }).collect(Collectors.joining(","));
            return res;
        } else {
            return "";
        }
    }

/*
    // test data
    public static void main(String[] args) {
        System.out.println(parse("CAZgRf820167151156145"));
        System.out.println(parse("RMuiRdf010160141151156164"));
        System.out.println(parse("lims8r3860lims1631411561441"));
        System.out.println(parse("GZQRyr6870GZQR+0041431501451451631455A"));
        System.out.println(parse("qkMfPjrd0561411551551651561511641511571567"));
        System.out.println(parse("EOcTkerf389-0201511431450551431621451411550"));

        //Output:
        //"CAZgRf820167151156145","CAZg","Rf820","false","true",,"wine"
        //"RMuiRdf010160141151156164","RMui","Rdf010","true","true",,"paint"
        //"lims8r3860lims1631411561441","lims","r3860lims","false","false",,"sand"
        //"GZQRyr6870GZQR+0041431501451451631455A","GZQR","r6870GZQR","false","false","+4","cheese"
        //"qkMfPjrd0561411551551651561511641511571567","qkMf","rd056","true","false",,"ammunition"
        //"EOcTkerf389-0201511431450551431621451411550","EOcT","rf389","false","true","-20","ice-cream"
    }
    */
}
