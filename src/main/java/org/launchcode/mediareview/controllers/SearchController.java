package org.launchcode.mediareview.controllers;

import org.launchcode.mediareview.models.Media;
import org.launchcode.mediareview.models.Review;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("search")
public class SearchController extends BaseController {

    static ArrayList<String> searchTypes = new ArrayList<>();

    public SearchController() {
        searchTypes.add("Review");
        searchTypes.add("Media");
        searchTypes.add("All");
    }

    public void makeList (String searchType, String searchTerm, ArrayList<Object> list) {
        if (searchType.equals("All")) {
            for (Review review : reviewDao.findAll()) {
                if (review.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        review.getText().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        review.getMedia().getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    list.add(review);
                }
            }
            for (Media media : mediaDao.findAll()) {
                if (media.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    list.add(media);
                }
            }
        } else if (searchType.equals("Review")) {
            for (Review review : reviewDao.findAll()) {
                if (review.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        review.getText().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        review.getMedia().getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    list.add(review);
                }
            }
        } else {
            for (Media media : mediaDao.findAll()) {
                if (media.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    list.add(media);
                }
            }
        }
    }

    @RequestMapping(value="")
    public String search(Model model, String searchType, String searchTerm) {
        model.addAttribute("title", "Search");
        model.addAttribute("types", searchTypes);
        if (searchType != null) {
            if (searchTerm == null || searchTerm.trim().length() == 0) {
                model.addAttribute("message", "Please enter something.");
                return "search";
            }
            ArrayList<Object> list = new ArrayList<>();
            makeList(searchType, searchTerm, list);
            model.addAttribute("items", list);
            if (list.size() < 1) {
                model.addAttribute("message", "Sorry, no results. :(");
            }
        }

        return "search";
    }
}