package org.launchcode.mediareview.controllers;

import org.launchcode.mediareview.models.Media;
import org.launchcode.mediareview.models.Review;
import org.launchcode.mediareview.models.data.MediaDao;
import org.launchcode.mediareview.models.data.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("media")
public class MediaController {

    @Autowired
    private MediaDao mediaDao;

    @Autowired
    private ReviewDao reviewDao;

    @RequestMapping(value="")
    public String index(Model model) {
        model.addAttribute("medias", mediaDao.findAll());
        model.addAttribute("title", "Media List");
        return "media/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddMediaForm(Model model) {
        model.addAttribute(new Media());
        model.addAttribute("title", "Add Media");
        return "media/add";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String processAddMediaForm(Model model, @ModelAttribute @Valid Media media, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Media");
            return "media/add";
        }

        mediaDao.save(media);
        return "redirect:/media/view/" + media.getId();
    }

    @RequestMapping(value="view/{mediaId}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable int mediaId) {
        Media media = mediaDao.findOne(mediaId);
        List<Review> reviews = media.getReviews();
        model.addAttribute("title", media.getTitle());
        model.addAttribute("reviews", reviews);
        model.addAttribute(new Review());
        model.addAttribute("mediaId", mediaId);
        return "media/view";
    }
}
