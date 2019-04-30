package tul.semestralka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tul.semestralka.service.CountryService;


@Controller
public class CountriesController {

    @Autowired
    private CountryService countryService;

    @GetMapping("")
    public String countries(Model model) {
        model.addAttribute("countries", countryService.getCountriesWithTown());
        return "index";
    }
}
