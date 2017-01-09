package pl.com.gurgul.dto;

import java.util.Date;

/**
 * Created by agurgul on 09.01.2017.
 */
public class MyVisitsReportRequestTO {

    private Date from;
    private Date to;

    public MyVisitsReportRequestTO(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public MyVisitsReportRequestTO() {
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
