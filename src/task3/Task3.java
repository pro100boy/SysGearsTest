package task3;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task3 {
    private static String FILENAME = "./decode.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String value;
        String[] dataArr;
        RouteData data = new RouteData();
        CsvFileWriter csv = new CsvFileWriter();

        System.out.print("Input encoded string: ");
        while (sc.hasNextLine()) {
            value = sc.nextLine();
            if (data.decodeData(value, true)) {
                dataArr = data.getData();
                System.out.println("Decoded data: ".concat(Arrays.toString(dataArr)));
                csv.writeCsvFile(FILENAME, dataArr);
            } else {
                System.out.println("Wrong data format");
            }
            System.out.print("Input encoded string: ");
        }
    }
}

class RouteData {
    private Pattern ptn = Pattern.compile("^([A-Za-z0-9]{4})[A-Za-z0-9]{0,2}([Rr](d)?(f)?(?:(?<=[df])(?:[0-9]{3})|(?:[0-9]{4}))(?:\\1)?)(?:([+-])(\\d{3}))?(\\d+)[a-zA-Z]{0,2}$");
    boolean hasData;
    private String[] dataArr = new String[7];

    // if trimZeros is true then temperature leading zeros will be trimmed (+007 -> +7)
    public boolean decodeData(String data, boolean trimZeros) {
        Matcher mtr = ptn.matcher(data);
        Arrays.fill(dataArr, "");
        if (mtr.matches()) {
            dataArr[0] = data.trim();
            dataArr[1] = mtr.group(1);
            dataArr[2] = mtr.group(2);
            if (mtr.group(3) != null) {
                dataArr[3] = "true";
            } else {
                dataArr[3] = "false";
            }
            if (mtr.group(4) != null) {
                dataArr[4] = "true";
            } else {
                dataArr[4] = "false";
            }
            if (mtr.group(5) != null) {
                if (trimZeros) {
                    dataArr[5] = mtr.group(5).concat(Integer.toString(Integer.parseInt(mtr.group(6))));
                } else {
                    dataArr[5] = mtr.group(5).concat(mtr.group(6));
                }
            }
            int i = 0;
            StringBuilder decStr = new StringBuilder();
            while (i < mtr.group(7).length() - 2) {
                decStr.append((char) Integer.parseInt(mtr.group(7).substring(i, i + 3), 8));
                i += 3;
            }
            dataArr[6] = decStr.toString();
            hasData = true;
            return true;
        } else {
            hasData = false;
            return false;
        }
    }

    public String[] getData() {
        return dataArr;
    }
}

class CsvFileWriter {
    private static final String DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String[] FILE_HEADER = {"шифр", "код водителя", "код путевого листа", "опасный", "хрупкий",
            "температура", "наименование"};
    private static boolean firstRun = true;

    private final class Encoding {
        public static final String WIN = "cp1251";
        public static final String UTF = "UTF-8";
    }

    public static void writeCsvFile(String fileName, String[] data) {
        BufferedWriter writer = null;
        File file = new File(fileName);
        try {
            if (firstRun) {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Encoding.WIN));
                for (int i = 0; i < FILE_HEADER.length - 1; i++) {
                    writer.append("\"".concat(FILE_HEADER[i]).concat("\""));
                    writer.append(DELIMITER);
                }
                writer.append("\"".concat(FILE_HEADER[FILE_HEADER.length - 1]).concat("\""));
                writer.append(NEW_LINE_SEPARATOR);
                firstRun = false;
            } else {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), Encoding.WIN));
            }
            for (int i = 0; i < data.length - 1; i++) {
                writer.append("\"".concat(data[i]).concat("\""));
                writer.append(DELIMITER);
            }
            writer.append("\"".concat(data[data.length - 1]).concat("\""));
            writer.append(NEW_LINE_SEPARATOR);
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter");
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter");
                e.printStackTrace();
            }
        }
    }
}