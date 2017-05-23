package task3;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

enum Encoding {
    WIN("CP1251"),
    UTF("UTF-8");
    private final String charset;

    Encoding(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }
}

public class Main {
    private final String FILENAME = "./decoded_data.csv";

    /**
     * Input coded string</br>
     * Input 'end' for finishing
     * @return {@code List<String>} of the entered lines
     */
    private final List<String> inputCodes() {
        final String EXITCODE = "end";
        final List<String> codeList = new LinkedList<>();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Please input code or 'end' to stop input: ");
            try {
                String codeStr = reader.readLine();
                if (codeStr.equals(EXITCODE)) break;
                else codeList.add(codeStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return codeList;
    }

    /**
     * Decoding entered lines
     * @param  codeList {@code List<String>} of the entered lines
     * @return {@code StringBuilder} which contain decoded lines
     */
    private final StringBuilder decodeData(List<String> codeList) {
        final String HEADER = "\"шифр\",\"код водителя\",\"код путевого листа\",\"опасный\",\"хрупкий\",\"температура\",\"наименование\"";
        StringBuilder decodedStrins = new StringBuilder(HEADER);

        codeList.stream().forEach(s -> decodedStrins.append(System.lineSeparator()).append(CodeParse.parse(s)));

        return decodedStrins;
    }

    /**
     * Save decoded lines to SCV file
     * @param decodedStrings {@code StringBuilder} which contain decoded lines
     * @param encoding charset value
     */
    private final void save2CSV(StringBuilder decodedStrings, Encoding encoding) {
        //append string buffer/builder to buffered writer
        try (Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILENAME), encoding.getCharset()))) {
            bw.append(decodedStrings);//Internally it does decodedStrings.toString();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<String> list = main.inputCodes();
        StringBuilder stringBuilder = main.decodeData(list);
        main.save2CSV(stringBuilder, Encoding.WIN);
        //System.out.println(stringBuilder.toString());
    }
}
