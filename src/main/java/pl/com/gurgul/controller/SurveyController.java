package pl.com.gurgul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.gurgul.model.Survey;
import pl.com.gurgul.repository.SurveyRepository;

import java.util.List;

/**
 * Created by agurgul on 12.10.2016.
 */
@RestController
@RequestMapping("/survey")
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    @RequestMapping("/all")
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }
}
