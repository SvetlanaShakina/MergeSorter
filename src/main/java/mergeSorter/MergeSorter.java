package mergeSorter;

import java.io.*;
import java.util.*;

public class MergeSorter {
    private static final List<ReaderFile> readerFileList = new ArrayList<>();
    private static String sortingMode;
    private static String dataType;
    private static String outputFileName;
    private static final List<String> inputFileNames = new ArrayList<>();
    private static PrintWriter writer = null;

    public static void main(String[] args) throws IOException {
        parseArguments(args);
        makeFileReaders();
        makeFileWriter();

        if (dataType.equals("-i")) {
            IntegerSorter integerSorter = new IntegerSorter(readerFileList, sortingMode);
            integerSorter.sort(writer);
        } else {
            StringSorter stringSorter = new StringSorter(readerFileList, sortingMode);
            stringSorter.sort(writer);
        }
    }

    private static void parseArguments(String[] args) {
        int countArgs = 0;

        if (!args[0].equals("-a") && !args[0].equals("-d") && !args[0].equals("-s") && !args[0].equals("-i")) {
            System.out.println("Некорректно указаны аргументы запуска программы");
            System.exit(0);
        }

        if (args[0].equals("-a") || args[0].equals("-d")) {
            sortingMode = args[0];
            countArgs++;
        } else
            sortingMode = "-a";

        if (args[0].equals("-s")) {
            dataType = "-s";
        } else if (args[0].equals("-i")) {
            dataType = "-i";
        } else if (args[1].equals("-s")) {
            dataType = "-s";
        } else {
            dataType = "-i";
        }
        countArgs++;

        outputFileName = args[countArgs++];
        inputFileNames.addAll(Arrays.asList(args).subList(countArgs, args.length));
    }

    private static void makeFileReaders() {
        for (String inputFileName : inputFileNames) {
            readerFileList.add(new ReaderFile(inputFileName));
        }
    }

    private static void makeFileWriter() throws IOException {
        writer = new PrintWriter(new FileWriter(outputFileName));
    }
}