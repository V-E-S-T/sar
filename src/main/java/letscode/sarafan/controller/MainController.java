package letscode.sarafan.controller;

import letscode.sarafan.domain.User;
import letscode.sarafan.repo.MessageRepo;
import letscode.sarafan.service.ProductService;
import letscode.sarafan.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageRepo messageRepo;
    private ProductService productService;
    private ReviewService reviewService;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(MessageRepo messageRepo, ProductService productService, ReviewService reviewService) {
        this.messageRepo = messageRepo;
        this.productService = productService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();

        if (user != null) {
            data.put("profile", user);
//            data.put("messages", messageRepo.findAll());
            data.put("products", productService.getAllProduct());
//            data.put("reviews", reviewService.getAllReviewsByAsin("some_asin"));
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
