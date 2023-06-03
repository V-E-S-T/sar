package letscode.sarafan.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@ToString(of = {"id", "asin", "title"})
@EqualsAndHashCode(of = {"id", "asin"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String asin;
    private String reference;
    private String price;
    @Column(columnDefinition = "text")

    private String description;

    @Column(columnDefinition = "text")
    private String title;
    private String rating;
    private String ratingCount;

    //TODO implement 'totalRatings' and 'totalReviews' parsing
    private Integer totalRatings;
    private Integer totalReviews;

    public Integer getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Integer totalRatings) {
        this.totalRatings = totalRatings;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String productRating) {
        this.rating = productRating;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String productRatingCount) {
        this.ratingCount = productRatingCount;
    }

    public Product(String asin, String reference, Long id) {
        this.asin = asin;
        this.reference = reference;
        this.id = id;
    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
