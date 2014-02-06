package ua.com.parfumkatalog;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author <a href="mailto:dmytro.barabash@playtech.com"> Dmytro Barabash</a> 2013-09-06 15:45
 */
public class ExcelImporter {

	public List<List<HSSFCell>> importExcelSheet(String fileName) {
		List<List<HSSFCell>> result = new ArrayList<List<HSSFCell>>();
		try {
			Workbook workBook = WorkbookFactory.create(new FileInputStream(fileName));
			Sheet sheet = workBook.getSheetAt(0);
			Iterator rowIter = sheet.rowIterator();

			while (rowIter.hasNext()) {
				HSSFRow row = (HSSFRow)rowIter.next();
				Iterator cellIter = row.cellIterator();
				List<HSSFCell> cellStoreVector = new ArrayList<HSSFCell>();

				while (cellIter.hasNext()) {
					HSSFCell cell = (HSSFCell)cellIter.next();
					cellStoreVector.add(cell);
				}
				result.add(cellStoreVector);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

}
