import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Reporter {
    public static void report() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet MD1Sheet = workbook.createSheet("MD1");
        XSSFSheet MD110Sheet = workbook.createSheet("MD110");
        XSSFSheet MD125Sheet = workbook.createSheet("MD125");
        XSSFSheet MD150Sheet = workbook.createSheet("MD150");

        System.out.println("Creating Simulation Data...");

        Object[][] MD1SimData = createMD1Data();
        Object[][] MD110SimData = createMD1KData(10);
        Object[][] MD125SimData = createMD1KData(25);
        Object[][] MD150SimData = createMD1KData(50);

        System.out.println("Loading data to Excel file...");

        loadToExcel(MD1SimData, MD1Sheet);
        loadToExcel(MD110SimData, MD110Sheet);
        loadToExcel(MD125SimData, MD125Sheet);
        loadToExcel(MD150SimData, MD150Sheet);

        try (FileOutputStream outputStream = new FileOutputStream("Simulation.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException error) {
            System.out.println("IOException:" + error);
        } finally {
            System.out.println("Excel files created");
        }
    }

    private static Object[][] createMD1Data() {
        Object[][] MD1SimData = new Object[1000][3];
        MD1SimData[0][0] = "E[T]";
        MD1SimData[0][1] = "P_IDLE";
        MD1SimData[0][2] = "E[N]";

        for (int lambda = 100, index = 1; lambda < 450; lambda = lambda + 1, index++) {
            Simulation simulation = new Simulation(lambda, 2000, 1000000, 10000000);
            simulation.start();
            MD1SimData[index][0] = simulation.calAvgSojournTime();
            MD1SimData[index][1] = simulation.calPercentIdle();
            MD1SimData[index][2] = simulation.calAvgBufferSize();
        }
        return MD1SimData;
    }

    private static Object[][] createMD1KData(int K) {
        Object[][] MD1KSimData = new Object[1000][4];
        MD1KSimData[0][0] = "E[T]";
        MD1KSimData[0][1] = "P_IDLE";
        MD1KSimData[0][2] = "E[N]";
        MD1KSimData[0][3] = "P_LOSS";

        for (int lambda = 250, index = 1; lambda < 750; lambda = lambda + 1, index++) {
            Simulation simulation = new Simulation(lambda, 2000, 1000000, K, 10000000);
            simulation.start();
            MD1KSimData[index][0] = simulation.calAvgSojournTime();
            MD1KSimData[index][1] = simulation.calPercentIdle();
            MD1KSimData[index][2] = simulation.calAvgBufferSize();
            MD1KSimData[index][3] = simulation.calPercentPacketLost();
        }
        return MD1KSimData;
    }

    private static void loadToExcel(Object[][] data, XSSFSheet sheet) {
        int rowCount = 0;
        for (Object[] aBook : data) {
            Row row = sheet.createRow(++rowCount);
            int columnCount = 0;
            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                }
            }

        }
    }
}
