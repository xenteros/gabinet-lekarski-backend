package pl.com.gurgul.service;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.gurgul.model.User;
import pl.com.gurgul.model.Visit;
import pl.com.gurgul.utils.CellStyles;
import pl.com.gurgul.utils.LoggedUserUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by agurgul on 09.01.2017.
 */
@Service
public class ExcelService {

    @Autowired
    VisitService visitService;

    @Autowired
    UserService userService;

    public HSSFWorkbook allVisits(Date from, Date to) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        CellStyles cellStyles = new CellStyles(workbook);
        HSSFSheet sheet = workbook.createSheet("Wizyty");
        List<HSSFRow> rows = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        headers.add("Imię");
        headers.add("Nazwisko");
        headers.add("Data");
        headers.add("Odbyta");
        Map<User, List<Visit>> content = new HashMap<>();
        List<User> doctors = userService.doctors();
        for (User doctor : doctors) {
            content.put(doctor, visitService.findVisitsByDoctor(doctor));
        }
        for (User doctor : content.keySet()) {
            rows.add(createHeader(sheet, cellStyles, doctor, rows.size()));
            rows.addAll(createContent(sheet,cellStyles, content.get(doctor), headers, rows.size()));
        }

        return workbook;
    }

    public HSSFWorkbook myVisits(Date from, Date to) {
        List<Visit> content;
        if (to == null) {
            to = new Date();
        }
        if (from == null) {
            content = visitService.findVisitsBetween(from, to);
        } else {
            content = visitService.findVisitsBetween(from, to);
        }

        List<String> headers = new ArrayList<>();
        headers.add("Imię");
        headers.add("Nazwisko");
        headers.add("Data");
        headers.add("Odbyta");
        HSSFWorkbook workbook = new HSSFWorkbook();
        CellStyles cellStyles = new CellStyles(workbook);
        HSSFSheet sheet = workbook.createSheet("Wizyty");
        List<HSSFRow> rows = new ArrayList<>();
        rows.add(createHeader(sheet, cellStyles));
        rows.addAll(createContent(sheet, cellStyles, content, headers,1));
        return workbook;
    }

    private HSSFRow createHeader(HSSFSheet sheet, CellStyles styles) {
        final HSSFRow row = sheet.createRow(0);
        styles.setHeaderStyle(row.createCell(1)).setCellValue("Imię");
        styles.setHeaderStyle(row.createCell(2)).setCellValue("Nazwisko");
        styles.setHeaderStyle(row.createCell(3)).setCellValue("Data");
        styles.setHeaderStyle(row.createCell(4)).setCellValue("Odbyta");
        return row;
    }

    private HSSFRow createHeader(HSSFSheet sheet, CellStyles styles, User user, int r) {
        final HSSFRow row = sheet.createRow(r);
        styles.setHeaderStyle(row.createCell(0)).setCellValue(user.getFirstName() + " " + user.getLastName());
        styles.setHeaderStyle(row.createCell(1)).setCellValue("Imię");
        styles.setHeaderStyle(row.createCell(2)).setCellValue("Nazwisko");
        styles.setHeaderStyle(row.createCell(3)).setCellValue("Data");
        styles.setHeaderStyle(row.createCell(4)).setCellValue("Odbyta");
        return row;
    }


    private List<HSSFRow> createContent(HSSFSheet sheet, CellStyles styles, List<Visit> content, List<String> headers, int r) {
        List<HSSFRow> rows = new ArrayList<>();
        int earned = 0;
        int currentRow = r;
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy h:mm");
        for (Visit v : content) {
            final HSSFRow row = sheet.createRow(currentRow);
            rows.add(row);
            styles.setContentStyle(row.createCell(1)).setCellValue(v.getUser().getFirstName());
            styles.setContentStyle(row.createCell(2)).setCellValue(v.getUser().getLastName());
            styles.setContentStyle(row.createCell(3)).setCellValue(df.format(v.getDate()));
            styles.setContentStyle(row.createCell(4)).setCellValue(v.getCompleted() ? "TAK" : "NIE");
            currentRow++;
            if (v.getCompleted()) {
                earned += v.getCost();
            }
        }
        final HSSFRow summary = sheet.createRow(currentRow);
        styles.setHeaderStyle(summary.createCell(3)).setCellValue("Zarobiono:");
        styles.setHeaderStyle(summary.createCell(4)).setCellValue(earned + "PLN");
        rows.add(summary);

        return rows;

    }

    public String myVisitsName(Date from, Date to) {
        User user = LoggedUserUtils.getLoggedUser();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy h:mm");
        if (from == null) {
            return user.getFirstName() + " " + user.getLastName() + " - wizyty przed " + df.format(to);
        }

        return user.getFirstName() + " " + user.getLastName() + " - wizyty między " + df.format(from) + " a " + df.format(to);


    }

}
