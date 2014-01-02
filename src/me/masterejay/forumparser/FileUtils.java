package me.masterejay.forumparser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author MasterEjay
 */
public class FileUtils {

    public static void save(String fileName, List<String> list) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));

        for (String title : list)
            pw.println(title);
        pw.close();
    }
}
