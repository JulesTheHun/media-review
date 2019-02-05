package org.launchcode.mediareview.controllers;


import org.launchcode.mediareview.models.Media;
import org.launchcode.mediareview.models.Review;
import org.launchcode.mediareview.models.data.MediaDao;
import org.launchcode.mediareview.models.data.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("review")
public class ReviewController {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private MediaDao mediaDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "Review List");
        model.addAttribute("reviews", reviewDao.findAll());

        return "review/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddReviewForm(Model model) {
        model.addAttribute(new Review());
        model.addAttribute("title", "Add Review");
        model.addAttribute("medias", mediaDao.findAll());
        return "review/add";
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    public String processAddReviewForm(Model model, @ModelAttribute @Valid Review review, Errors errors, @RequestParam int mediaId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Review");
            model.addAttribute("medias", mediaDao.findAll());
            return "review/add";
        }

        Media media = mediaDao.findOne(mediaId);
        review.setMedia(media);
        reviewDao.save(review);
        return "redirect:/review/view" + review.getId();
    }

    @RequestMapping(value="view/{reviewId}", method=RequestMethod.GET)
    public String view(Model model, @PathVariable int reviewId) {
        Review review = reviewDao.findOne(reviewId);
        model.addAttribute("review", review);
        return "review/view";
    }

    @RequestMapping(value="edit/{reviewId}", method=RequestMethod.GET)
    public String viewEditReviewForm(Model model, @PathVariable int reviewId) {
        Review review = reviewDao.findOne(reviewId);
        model.addAttribute("title", "Edit Review");
        model.addAttribute(review);
        return "review/edit";
    }

    @RequestMapping(value="edit", method=RequestMethod.POST)
    public String processEditReviewForm(Model model, @ModelAttribute @Valid Review review, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Review");
            return "redirect:/review/edit" + review.getId();
        }

        reviewDao.save(review);

        return "redirect:/review/view" + review.getId();
    }
}
