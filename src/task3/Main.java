package task3;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private final String FILENAME = "./decoded_data.csv";

    private final List<String> inputCodes()
    {
        final String EXITCODE = "end";
        final List<String> codeList = new LinkedList<>();

        while (true)
        {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Please input code: ");
                String codeStr = reader.readLine();
                if (codeStr.equals(EXITCODE)) break;
                else codeList.add(codeStr);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return codeList;
    }

    private final StringBuilder decodeData(List<String> codeList)
    {
        final String HEADER = "\"шифр\",\"код водителя\",\"код путевого листа\",\"опасный\",\"хрупкий\",\"температура\",\"наименование\"";
        StringBuilder decodedStrins = new StringBuilder(HEADER);

        codeList.stream().forEach(s -> decodedStrins.append(CodeParse.parse(s)).append(System.lineSeparator()));

        return decodedStrins;
    }

    private final void save2CSV(StringBuilder decodedStrins){
        //append string buffer/builder to buffered writer
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            bw.append(decodedStrins);//Internally it does decodedStrins.toString();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<String> list = main.inputCodes();
    }
}
