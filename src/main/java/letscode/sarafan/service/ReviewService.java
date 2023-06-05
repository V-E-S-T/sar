package letscode.sarafan.service;

import letscode.sarafan.Util.ParserUtil;
import letscode.sarafan.domain.Review;
import letscode.sarafan.repo.ProductRepo;
import letscode.sarafan.repo.ReviewRepo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final ProductRepo productRepo;

    @Autowired
    public ReviewService(ReviewRepo reviewRepo, ProductRepo productRepo) {
        this.reviewRepo = reviewRepo;
        this.productRepo = productRepo;
    }
    public Review save(Review review) {
        return reviewRepo.save(review);
    }
    public List<Review> parseReviewsFromPage(String pageReference){
        Document doc = ParserUtil.getHTMLDocument(pageReference);

//        Optional.ofNullable(doc.selectFirst("div[data-hook=\"reviews-medley-footer\"] a")).ifPresent(
//                element -> seeAllReviewsReference.append("https://www.amazon.com").append(element.attr("href")));
//        if (!seeAllReviewsReference.toString().equals("")){
        //doc = ParserUtil.getHTMLDocument(seeAllReviewsReference.toString());

//        doc.select("div#reviews-sort-order-options > select option[value=recent]");

        Elements reviews = doc.select("div[data-hook=review]");

        return reviews.stream().map(this::extractReviewFromElement).collect(Collectors.toList());

    }

    private Review extractReviewFromElement(Element reviewElement){
        Review review = new Review();
        review.setAmazonReviewId(reviewElement.id());
        Optional.ofNullable(reviewElement.selectFirst("span[data-hook='review-date']")).ifPresent(
                element -> {
                    review.setReviewDate(extractReviewDate(element.text()));
                    review.setReviewCountry(extractReviewCountry(element.text()));
                });
        review.setReviewTitle(Optional.ofNullable(reviewElement.selectFirst("a[data-hook='review-title'] span"))
                .map(element -> element.text())
                .orElse(""));
        review.setProductAsin(Optional.ofNullable(reviewElement.selectFirst("a[data-hook='review-title']"))
                .map(element -> element.attr("href").split("(.*)ASIN=")[1])
                .orElse(""));
        Optional.ofNullable(reviewElement.selectFirst("div[class='a-row'] a[class='a-link-normal']")).ifPresent(
                element ->{
                    review.setRating(Integer.parseInt(element.attr("title").split("\\.")[0]));
                    review.setSentiment(extractSentiment(review.getRating()));
                });
        review.setReview(Optional.ofNullable(reviewElement.selectFirst("span[data-hook='review-body']"))
                .map(element -> element.text())
                .orElse(""));
        return review;
    }

    private LocalDate extractReviewDate(String reviewDateAndCountry){
        String [] st = reviewDateAndCountry.split(" on ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        return LocalDate.parse(st[1], formatter);
    }

    private String extractReviewCountry(String reviewDateAndCountry){
        Pattern regex = Pattern.compile("((?<= in )(.*)(?= on ))");
        Matcher matcher = regex.matcher(reviewDateAndCountry);
        return matcher.find()
                ? matcher.group(0).replace("the ", "")
                : "";
    }

    private String extractSentiment(Integer rating){
        if (rating > 3){
            return "positive";
        } else if (rating < 3){
            return "negative";
        }
        return "neutral";
    }

    public List<Review> getAllReviewsByAsin(String asin) {
        return parseReviewsFromPage("https://www.amazon.com/TICARVE-Cleaning-Detailing-Interior-Assecories/product-reviews/B0988X73FW/ref=cm_cr_arp_d_viewopt_srt?ie=UTF8&reviewerType=all_reviews&sortBy=recent&pageNumber=1");
    }
}
