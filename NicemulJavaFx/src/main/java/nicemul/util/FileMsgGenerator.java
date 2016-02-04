package nicemul.util;
import java.io.File;
import java.util.Scanner;

/**
 * Provides methods to generate a String from a file.
 */
public class FileMsgGenerator {

        /**
         * @param filename Filename
         * @return String from file
         * @throws Exception if exception
         */
        public static String generateStringFromFile(String filename)  throws Exception {

                String generated = null;

                File inputFile = new File(filename);
                Scanner sc = new Scanner(inputFile);
                while (sc.hasNext()) {
                        generated = generated == null ? sc.nextLine() : generated +"\n" + sc.nextLine();
                }
                sc.close();
                return generated;
        }
}
