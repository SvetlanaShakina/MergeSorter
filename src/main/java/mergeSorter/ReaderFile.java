package mergeSorter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReaderFile {
    private BufferedReader reader;
    private String name;
    private int defaultValueMin = Integer.MIN_VALUE;
    private int defaultValueMax = Integer.MAX_VALUE;
    private String tempValue;

    public ReaderFile(String name) {
        try {
            this.reader = new BufferedReader(new FileReader(name));
            this.name = name;
        } catch (FileNotFoundException exception) {
            System.out.printf("Файл %s с исходными данными не найден", name);
            System.exit(0);
        }
    }

    public String getName() {
        return name;
    }

    protected int readLineInt(String sortingMode) throws IOException {
        String str = reader.readLine();
        int x = Integer.parseInt(str);
        boolean isSorted;
        if (sortingMode.equals("-a")) {
            isSorted = isSortedIntByAsc(x);
            defaultValueMin = x;
        } else {
            isSorted = isSortedIntByDesc(x);
            defaultValueMax = x;
        }
        if (!isSorted) throw new IOException();
        return x;
    }

    protected String readLineString(String sortingMode) throws IOException {
        String str = reader.readLine();
        boolean isSorted;
        if (tempValue == null) {
            tempValue = str;
            isSorted = true;
        } else {
            if (sortingMode.equals("-a")) {
                isSorted = isSortedStringByAsc(str);
            } else {
                isSorted = isSortedStringByDesc(str);
            }
            tempValue = str;
        }
        if (!isSorted) throw new IOException();
        return str;
    }

    protected boolean ready() throws IOException {
        return reader.ready();
    }

    private boolean isSortedStringByAsc(String nextString) {
        return (tempValue.compareTo(nextString) < 0);
    }

    private boolean isSortedStringByDesc(String nextString) {
        return (tempValue.compareTo(nextString) > 0);
    }

    private boolean isSortedIntByAsc(int num) {
        return (num > defaultValueMin);
    }

    private boolean isSortedIntByDesc(int num) {
        return (num < defaultValueMax);
    }
}
