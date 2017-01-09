package pl.com.gurgul.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;

/**
 * Created by agurgul on 09.01.2017.
 */
public class CellStyles {

    private final HSSFCellStyle header;
    private final HSSFCellStyle content;

    public CellStyles(HSSFWorkbook workbook) {

        header = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        header.setFont(font);

        content = workbook.createCellStyle();
        content.setWrapText(true);
    }

    public Cell setHeaderStyle(Cell cell) {
        cell.setCellStyle(header);
        return cell;
    }

    public Cell setContentStyle(Cell cell) {
        cell.setCellStyle(content);
        return cell;
    }
}
