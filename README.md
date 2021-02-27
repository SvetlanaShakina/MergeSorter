Программа MergeSorter выполняет сортировку слиянием нескольких файлов, в которых содержатся строки или целочисленные значения. Данные в каждом файле должны быть записаны в столбик (одна строка - одно значение). Предполагается, что содержимое входных файлов отсортировано. Если это условие нарушено, данные будут частично потеряны.
Запуск программы производится из командной строки, должны быть заданы следующие аргументы:
1. режим сортировки (-a или -d) - необязательно, если аргумент не указан, производится сортировка по возрастанию;
2. тип данных (-s или -i) - обязательно;
3. имя выходного файла - обязательно;
4. имена входных файлов - обязательно, не меньше одного.

Пример запуска программы:
1. java -jar mergeSort-1.0.jar -a -i result.txt input1.txt input2.txt (сортировка целых чисел по возрастанию)
2. java -jar mergeSort-1.0.jar -d -s result.txt input1.txt input2.txt (сортировка строк по убыванию)
 