package pl.com.gurgul.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.com.gurgul.dto.MyVisitsReportRequestTO;
import pl.com.gurgul.service.ExcelService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.lang.String.format;

/**
 * Created by agurgul on 09.01.2017.
 */
@RestController
@RequestMapping(value = "/api/report")
public class RaportController {

    @Autowired
    ExcelService excelService;

    @RequestMapping(value = "/myVisits", method = RequestMethod.GET)
    public void myVisitsReport(HttpServletResponse response, @RequestParam Long from, @RequestParam Long to) {

        HSSFWorkbook workbook = excelService.myVisits(new Date(from), new Date(to));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = excelService.myVisitsName(new Date(from), new Date(to)) + ".xls";
        response.setHeader("Content-disposition", format("attachment; filename=%s", fileName));
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/allVisits", method = RequestMethod.GET)
    public void allyVisitsReport(HttpServletResponse response, @RequestParam Long from, @RequestParam Long to) {

        HSSFWorkbook workbook = excelService.allVisits(new Date(from), new Date(to));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = excelService.myVisitsName(new Date(from), new Date(to)) + ".xls";
        response.setHeader("Content-disposition", format("attachment; filename=%s", fileName));
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
