package pl.com.gurgul.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.com.gurgul.dto.VisitTO;
import pl.com.gurgul.model.Visit;
import pl.com.gurgul.repository.VisitRepository;
import pl.com.gurgul.service.VisitService;
import pl.com.gurgul.utils.LoggedUserUtils;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by agurgul on 10.12.2016.
 */
@RestController
@RequestMapping("/api/visit")
public class VisitController {

    private final Logger LOG = LoggerFactory.getLogger(VisitController.class);

    @Autowired
    VisitService visitService;

    @ApiOperation(value = "addVisit", nickname = "addVisit")
    @PreAuthorize("hasAuthority(T(pl.com.gurgul.utils.UserRoles).ROLE_DOCTOR)")
    @RequestMapping(value = "/add", method = POST)
    public Visit newVisit(@RequestBody VisitTO to) {
        LOG.info("Received request to add new visit.");
        return visitService.createVisit(to);
    }

    @RequestMapping(value = "/update", method = PUT)
    @PreAuthorize("hasAuthority(T(pl.com.gurgul.utils.UserRoles).ROLE_DOCTOR)")
    public Visit updateVisit(@RequestBody VisitTO to) {
        LOG.info("Received request to update existing visit.");
        return visitService.updateVisit(to);
    }
    @PreAuthorize("hasAuthority(T(pl.com.gurgul.utils.UserRoles).ROLE_DOCTOR)")
    @RequestMapping(value = "/get/{id}", method = GET)
    public Visit findVisit(@PathVariable String id) {
        LOG.info("Received request for a visit.");
        return visitService.findVisit(id);
    }

    @PreAuthorize("hasAuthority(T(pl.com.gurgul.utils.UserRoles).ROLE_DOCTOR)")
    @RequestMapping(value = "/get/all", method = GET)
    public List<Visit> findAll() {
        LOG.info("Received request for all visits.");
        return visitService.findAll();
    }

    @PreAuthorize("hasAuthority(T(pl.com.gurgul.utils.UserRoles).ROLE_USER)")
    @RequestMapping(value = "/get/my", method = GET)
    public List<Visit> findLoggedUsersVisits() {
        LOG.info("Received request for all user's visits.");
        return visitService.findByUuid(LoggedUserUtils.getLoggedUser().getUuid());
    }

    @PreAuthorize("hasAuthority(T(pl.com.gurgul.utils.UserRoles).ROLE_DOCTOR)")
    @RequestMapping(value = "/count/today", method = GET)
    public int countToday() {
        LOG.info("Received request for logged doctor's left visits today count.");
        return visitService.countTodays();
    }
}
