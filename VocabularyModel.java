import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VocabularyModel {
    private static final String FILE_NAME = "src\\res\\db";
    private static final int CYRILLIC = 0;
    private static final int LATIN = 1;


    public static void main(String[] args) {
    }


    public int getLastNumber() {
        String lastLine = getLastLine();

        return Integer.parseInt(lastLine.split(" ")[0]);
    }

    public String getLastLine() {
        String item = null;
        try {
            FileReader reader = new FileReader(FILE_NAME);
            Scanner scan = new Scanner(reader);
            while (scan.hasNext()) {
                item = scan.nextLine();
            }
        } catch (IOException e) {
            System.out.println("File's not found");
            return null;
        }
        return item;
    }


    public boolean setData(String data) {
        if (!isValid(data)) return false;

        try {

            FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            String nextItem = "\n" + (getLastNumber() + 1) + " " + data;
            fileWriter.write(nextItem);
            fileWriter.flush();

        } catch (IOException e) {
            System.out.println("File not found! Check your fileName or file's location");
        }


        return false;
    }


    public boolean isValid(String data) {
        String[] arr = data.split(" ");
        try {

            if (!isCyrillic(arr[0])) {
                return false;
            }

            int flag = CYRILLIC;

            for (int i = 1; i < arr.length; ++i) {

                if (isLatin(arr[i])) {
                    flag = LATIN;
                    continue;
                }

                if (isCyrillic(arr[i]) && flag == LATIN) {
                    return false;
                }

                if (!isCyrillic(arr[i]) && !isLatin(arr[i])) {
                    return false;
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }


    // It works correct
    public boolean isCyrillic(String data) {
        char[] chrArr = data.toCharArray();
        for (char c : chrArr) {
            if (c < '\u0400' || c > '\u04FF') return false;
        }
        return true;
    }

    // It works correct
    public boolean isLatin(String data) {
        char[] chrArr = data.toCharArray();
        for (char c : chrArr) {
            if ((c < '\u0041' | c > '\u005A') & (c < '\u0061' | c > '\u007A')) {
                return false;
            }
        }
        return true;
    }
}