import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompareFiles {

    public void compareFilesByLine(Path path1, Path path2, FileWriter outputFile) throws IOException {
        removeEmptyLines(path1);
        removeEmptyLines(path2);

        try (BufferedReader bf1 = Files.newBufferedReader(path1);
             BufferedReader bf2 = Files.newBufferedReader(path2)) {

            List<Long> lines = new ArrayList<>();
            long numberOfLinesWithDifferences = findLinesWithDifferences(path1, path2);
            Long lineNumber = 1L;
            String line1, line2;
            while ((line1 = bf1.readLine()) != null) {
                line2 = bf2.readLine();
                if (!line1.equals(line2) && lines.isEmpty()) {
                    outputFile.write(path1 + "  " + path2 + "  Number of lines with differences: " + numberOfLinesWithDifferences + "\n");
                }

                if(line2 != null && (line1.startsWith("agenda-group") || line2.startsWith("agenda-group"))){
                    continue;
                }

                if (!line1.equals(line2)) {
                    outputFile.write("Line " + lineNumber + ": " + line1 + "\n");
                    outputFile.write("Line " + lineNumber + ": " + line2 + "\n");
                    lines.add(lineNumber);
                }
                lineNumber++;
            }
        }
    }

    private static long findLinesWithDifferences(Path path1, Path path2) throws IOException {
        try (BufferedReader bf1 = Files.newBufferedReader(path1);
             BufferedReader bf2 = Files.newBufferedReader(path2)) {
            return getCountLines(bf1, bf2);
        }
    }

    private static long getCountLines(BufferedReader bf1, BufferedReader bf2) throws IOException {
        List<Long> lines = new ArrayList<>();
        Long lineNumber = 1L;
        String line1, line2;
        while ((line1 = bf1.readLine()) != null) {
            line2 = bf2.readLine();
            if(line2 != null && (line1.startsWith("agenda-group") || line2.startsWith("agenda-group"))){
                continue;
            }

            if (!line1.equals(line2)) {
                lines.add(lineNumber);
            }
            lineNumber++;
        }
        return getCountLines(lines);
    }

    private static long getCountLines(List<Long> lines) {
        long countLines = 0;
        for(Long ignored : lines){
            countLines++;
        }
        return countLines;
    }

    private void removeEmptyLines(Path OldFile) {
        Scanner file;
        PrintWriter writer;
        File file1 = new File(OldFile.toUri());
        File file2 = new File("newFile.txt");
        try {

            file = new Scanner(file1);
            writer = new PrintWriter(file2);

            while (file.hasNext()) {
                String line = file.nextLine();
                if (!line.isEmpty()) {
                    writer.write(line);
                    writer.write("\n");
                }
            }

            file.close();
            writer.close();

            file1.delete();
            file2.renameTo(file1);

        } catch (FileNotFoundException ex) {
            System.out.println("No file found");
        }
    }
}
