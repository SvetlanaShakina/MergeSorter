package mergeSorter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StringSorter extends Sorter {
    private final List<ReaderFile> readerFileList;
    private final String sortingMode;

    public StringSorter(List<ReaderFile> readerFileList, String sortingMode) {
        this.readerFileList = readerFileList;
        this.sortingMode = sortingMode;
    }

    protected void sort(PrintWriter writer) throws IOException {
        List<String> buffer = new ArrayList<>();
        for (ReaderFile readerFile : readerFileList) {
            buffer.add(readerFile.readLineString(sortingMode));
        }

        while (!readerFileList.isEmpty()) {
            String value;
            if (sortingMode.equals("-a")) {
                value = findMin(buffer);
            } else {
                value = findMax(buffer);
            }
            writer.println(value);
            int indexRemovedElem = buffer.indexOf(value);
            ReaderFile currentReader = readerFileList.get(indexRemovedElem);
            if (currentReader.ready()) {
                bufferSet(buffer, indexRemovedElem, currentReader);
            } else {
                buffer.remove(indexRemovedElem);
                readerFileList.remove(currentReader);
            }
        }
        writer.close();
    }

    private void bufferSet(List<String> buffer, int indexRemovedElem, ReaderFile currentReader) throws IOException {
        if (currentReader.ready()) {
            try {
                buffer.set(indexRemovedElem, currentReader.readLineString(sortingMode));
            } catch (IOException e) {
                System.out.printf("Нарушена сортировка строк в файле %s, возможна потеря данных\n", currentReader.getName());
                buffer.remove(indexRemovedElem);
                readerFileList.remove(indexRemovedElem);
            }
        } else {
            buffer.remove(indexRemovedElem);
            readerFileList.remove(indexRemovedElem);
        }
    }

    private static String findMin(List<String> buffer) {
        String min = buffer.get(0);
        for (String string : buffer) {
            if (min.compareTo(string) > 0)
                min = string;
        }
        return min;
    }

    private static String findMax(List<String> buffer) {
        String max = buffer.get(0);
        for (String string : buffer) {
            if (max.compareTo(string) < 0)
                max = string;
        }
        return max;
    }

}
