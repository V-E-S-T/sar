package letscode.sarafan.controller;

import letscode.sarafan.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
//    @GetMapping("{asin}")
//    public List<Review> listReviews(@PathVariable("asin") String asin){
//        return reviewService.getAllReviewsByAsin(asin);
//    }

//    @GetMapping
//    public String main(Model model){
//        model.addAttribute("frontendData", reviewService.getAllReviewsByAsin("B0988X73FW"));
//        return "review";
//    }
}
