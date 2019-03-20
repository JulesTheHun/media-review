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

    public void searchResults (String searchTerm, String searchType, ArrayList<Object> list) {
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

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("title", "Search");
        model.addAttribute("types", searchTypes);
        return "search";
    }

    @RequestMapping(value = "results")
    public String results(Model model, String searchType, String searchTerm) {
        ArrayList<Object> list = new ArrayList<>();
        searchResults(searchTerm, searchType, list);
        model.addAttribute("types", searchTypes);
        model.addAttribute("items", list);

        return "search";
    }
}