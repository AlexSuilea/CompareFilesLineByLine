import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        CompareFiles compFil = new CompareFiles();

        FileWriter outputFile = new FileWriter("output/output.txt");

        for (int i = 1; i <= 5; i++) {
            try {
                compFil.compareFilesByLine(Path.of("folder1/file" + i + ".txt"), Path.of("folder2/file" + i + ".txt"), outputFile);
            } catch (NoSuchFileException ex) {
                System.out.println("Files with number " + i + " were not found");
            }
        }
        outputFile.close();
    }
}