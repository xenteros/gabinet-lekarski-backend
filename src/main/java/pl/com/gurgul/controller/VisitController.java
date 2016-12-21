package pl.com.gurgul.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.com.gurgul.dto.VisitTO;
import pl.com.gurgul.model.Visit;
import pl.com.gurgul.repository.VisitRepository;
import pl.com.gurgul.service.VisitService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by agurgul on 10.12.2016.
 */
@RestController
@RequestMapping("/api/visit")
public class VisitController {

    @Autowired
    VisitService visitService;

    @ApiOperation(value = "addVisit", nickname = "addVisit")
    @RequestMapping(value = "/add", method = POST)
    public Visit newVisit(@RequestBody VisitTO to) {
        return visitService.createVisit(to);
    }

    @RequestMapping(value = "/update", method = PUT)
    public Visit updateVisit(@RequestBody VisitTO to) {

        return null;
    }

    @RequestMapping(value = "/get/{id}", method = GET)
    public Visit findVisit(@PathVariable String id) {
        return visitService.findVisit(id);
    }

    @RequestMapping(value = "/get/all", method = GET)
    public List<Visit> findAll() {
        return visitService.findAll();
    }
    @RequestMapping(value = "/get/{uuid}/user", method = GET)
    public List<Visit> findByUuid(@PathVariable String uuid) {
        return visitService.findByUuid(uuid);
    }
    @RequestMapping(value = "/count/today", method = GET)
    public int countToday() {
        return visitService.countTodays();
    }
}
