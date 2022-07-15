package utils.common;


import com.opencsv.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVUtils {

    /**
     * Update CSV by row and column
     *
     * @param fileToUpdate CSV file path to update e.g. D:\\abc\\test.csv
     * @param replace      Replacement for your cell value
     * @param row          Row for which need to update, will start from 1 while ignoring header
     * @param col          Column for which you need to update, will start from 0 for 1st column
     * @throws IOException
     */
    public static void updateCSVColumnForARow(String fileToUpdate, String replace,
                                              int row, int col) throws Exception {

        File inputFile = new File(fileToUpdate);

        // Read existing file
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(inputFile)).withCSVParser(csvParser).build();
        List<String[]> csvBody = reader.readAll();
        // get CSV row column  and replace with by using row and column
        csvBody.get(row)[col] = replace;
        reader.close();

        // Write to CSV file which is open
        ICSVWriter writer = new CSVWriterBuilder(new FileWriter(inputFile)).withSeparator(CSVWriter.DEFAULT_SEPARATOR).build();
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }

    /**
     * Update CSV by row and column
     *
     * @param fileToUpdate CSV file path to update e.g. D:\\abc\\test.csv
     * @param replace      Replacement for your cell value
     * @param rowCount          total number of Rows count for which we need to update, should start from 1 while ignoring header
     * @param col          Column for which you need to update, will start from 0 for 1st column
     * @throws IOException
     */
    public static void updateCSVColumnForAllRows(String fileToUpdate, String replace,
                                                 int rowCount, int col) throws Exception {
        File inputFile = new File(fileToUpdate);

        // Read existing file
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(inputFile)).withCSVParser(csvParser).build();
        List<String[]> csvBody = reader.readAll();
        // get CSV row column  and replace with by using row and column
        for (int i = 1; i <= rowCount; i++) {
            csvBody.get(i)[col] = replace;
        }
        reader.close();

        // Write to CSV file which is open
        ICSVWriter writer = new CSVWriterBuilder(new FileWriter(inputFile)).withSeparator(CSVWriter.DEFAULT_SEPARATOR).build();
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }

    /**
     * Update CSV body
     *
     * @param fileToUpdate CSV file path to update e.g. D:\\abc\\test.csv
     * @param body      Replacement for your table content
     * @throws IOException
     */
    public static void updateCSV(String fileToUpdate, List<String[]> body) throws Exception {

        File inputFile = new File(fileToUpdate);
        // Read existing file
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(inputFile)).withCSVParser(csvParser).build();
        List<String[]> csvBody = body;
        // Write to CSV file which is open
        ICSVWriter writer = new CSVWriterBuilder(new FileWriter(inputFile)).withSeparator(CSVWriter.DEFAULT_SEPARATOR).build();
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }
}
