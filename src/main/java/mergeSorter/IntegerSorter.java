package mergeSorter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class IntegerSorter extends Sorter {
    private final List<ReaderFile> readerFileList;
    private final String sortingMode;

    public IntegerSorter(List<ReaderFile> readerFileList, String sortingMode) {
        this.readerFileList = readerFileList;
        this.sortingMode = sortingMode;
    }

    protected void sort(PrintWriter writer) throws IOException {
        List<Integer> buffer = new ArrayList<>();
        for (ReaderFile readerFile : readerFileList) {
            buffer.add(readerFile.readLineInt(sortingMode));
        }

        while (!readerFileList.isEmpty()) {
            int value;
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

    private void bufferSet(List<Integer> buffer, int indexRemovedElem, ReaderFile currentReader) throws IOException {
        if (currentReader.ready()) {
            try {
                buffer.set(indexRemovedElem, currentReader.readLineInt(sortingMode));
            } catch (NumberFormatException e) {
                System.out.printf("Возможна потеря данных: в файле %s есть нецелочисленные значения\n", currentReader.getName());
                bufferSet(buffer, indexRemovedElem, currentReader);
            } catch (IOException e) {
                System.out.printf("Нарушена сортировка чисел в файле %s, возможна потеря данных\n", currentReader.getName());
                buffer.remove(indexRemovedElem);
                readerFileList.remove(indexRemovedElem);
            }
        } else {
            buffer.remove(indexRemovedElem);
            readerFileList.remove(indexRemovedElem);
        }
    }

    private static int findMin(List<Integer> buffer) {
        int min = buffer.get(0);
        for (int integer : buffer) {
            if (min > integer)
                min = integer;
        }
        return min;
    }

    private static int findMax(List<Integer> buffer) {
        int max = buffer.get(0);
        for (int integer : buffer) {
            if (max < integer)
                max = integer;
        }
        return max;
    }
}
