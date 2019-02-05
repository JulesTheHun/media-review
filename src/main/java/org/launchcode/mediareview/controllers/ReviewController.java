package org.launchcode.mediareview.controllers;


import org.launchcode.mediareview.models.data.MediaDao;
import org.launchcode.mediareview.models.data.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private MediaDao mediaDao;

    public String index(Model model) {
        model.addAttribute("title", "Review List");
        model.addAttribute("reviews", reviewDao.findAll());

        return "review/index";
    }
}
