package casmex.utility;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ExcelUtility {
	private static FileInputStream excelFile;
	private static XSSFWorkbook excelWBook;
	private static XSSFSheet excelWSheet;
	private static XSSFRow excelRow;
	private static XSSFCell excelCell;

	private LogUtility log4j;

	ArrayList<String> list;
	public static int lastRowNumber;
	public static int lastColNumber;

	public ExcelUtility() {
		log4j = new LogUtility();
	}

	public void setExcelConfig(String inputPath, String inputSheet) {
		try {
			// Config Input Excel File
			excelFile = new FileInputStream(inputPath);
			excelWBook = new XSSFWorkbook(excelFile);
			excelWSheet = excelWBook.getSheet(inputSheet);
			lastRowNumber = excelWSheet.getLastRowNum();
		} catch (IOException sysEx) {
			log4j.error("setExcelConfig", sysEx);
		}
	}

	public void writeResultToExcel(Map<String, String> result, String outputFilePath, String outputSheetName) {
		// Write Results to Result Sheet
		try {
			excelWSheet = excelWBook.getSheet(outputSheetName);
			int iRow = 1;
			// Iterate through HashMap to get the Stored Results (Key, Value)
			for (Map.Entry<String, String> entry : result.entrySet()) {
				setCellData(iRow, 0, entry.getKey());
				setCellData(iRow, 1, entry.getValue());
				iRow++;
			}
			excelFile.close();
			FileOutputStream excelFile = new FileOutputStream(outputFilePath);
			excelWBook.write(excelFile);
			excelFile.close();
		} catch (IOException sysEx) {
			log4j.error("writeResultToExcel", sysEx);
		}
	}

	public ArrayList<String> getRowData(int rowNum, int fromColNum, int toColNum) {
		ArrayList<String> list = new ArrayList<String>();
		lastColNumber = toColNum - 1;
		for (int colNum = fromColNum; colNum <= lastColNumber; colNum++) {
			list.add(getCellData(rowNum, colNum));
		}
		return list;
	}

	public ArrayList<String> getRowData(int rowNum, int fromColNum) {
		ArrayList<String> list = new ArrayList<String>();
		lastColNumber = excelWSheet.getRow(rowNum).getPhysicalNumberOfCells();
		lastColNumber = lastColNumber - 1;
		for (int colNum = fromColNum; colNum <= lastColNumber; colNum++) {
			list.add(getCellData(rowNum, colNum));
		}
		return list;
	}

	// public ArrayList<String> storeTestDetails(int fromRow, int toRow) {
	// ArrayList<String> list = new ArrayList<String>();
	// for(int row = fromRow; row <= toRow; row++) {
	// list.add(getCellData(row, 0));
	// list.add(getCellData(row, 1));
	// }
	// return list;
	// }

	public void setCellData(int iRow, int iCell, String data) {
		// Write Data to a particular cell in Excel File
		// Retrieve the row and check for null
		excelRow = excelWSheet.getRow(iRow);
		if (excelRow == null) {
			excelRow = excelWSheet.createRow(iRow);
		}
		// Update the value of cell
		excelCell = excelRow.getCell(iCell);
		if (excelCell == null) {
			excelCell = excelRow.createCell(iCell);
		}
		excelCell.setCellValue(data);
	}

	public String getCellData(int iRow, int iCell) {
		// To get Data present in a Excel Cell
		String cellData;
		try {
			excelCell = excelWSheet.getRow(iRow).getCell(iCell);
			cellData = excelCell.getStringCellValue();
		} catch (Exception ex) {
			ex.printStackTrace();
			cellData = "EmptyCell";
		}
		return cellData;
	}
}