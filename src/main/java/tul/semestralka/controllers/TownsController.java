package tul.semestralka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tul.semestralka.data.Town;
import tul.semestralka.service.TownService;

import java.util.List;


@Controller
public class TownsController {

    @Autowired
    private TownService townService;

    @GetMapping("/{countryName}")
    public String findByCode(@PathVariable String countryName, Model model) {
        List<Town> towns =  townService.getTownsByCountryCode(countryName, true);

        model.addAttribute("townsData", towns);
        return "towns";
    }
}
