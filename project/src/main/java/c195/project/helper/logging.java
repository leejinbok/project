package c195.project.helper;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class logging {
    public static String loginFile = "logon_activity.txt";

    FileWriter fileWriter = new FileWriter(loginFile, true);
    PrintWriter printWriter = new PrintWriter(fileWriter);

    public void loginLog() {
        printWriter.println();
    }

    public logging() throws IOException {
    }
}
