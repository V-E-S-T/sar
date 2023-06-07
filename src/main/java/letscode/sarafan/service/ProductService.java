package letscode.sarafan.service;

import letscode.sarafan.Util.ParserUtil;
import letscode.sarafan.domain.Product;
import letscode.sarafan.repo.ProductRepo;
import letscode.sarafan.repo.ReviewRepo;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final ReviewRepo reviewRepo;

    @Autowired
    public ProductService(ReviewRepo reviewRepo, ProductRepo productRepo) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
    }

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public Product findByAsin(String asin) {
        return productRepo.findByAsin(asin);
    }

    public boolean deleteByAsin(String asin) {
//        Integer p = productRepo.deleteProductByAsin(asin);
//        Integer r = reviewRepo.deleteReviewsByProductAsin(asin);
        Integer i = productRepo.deleteProductByAsin(asin);
        return i > 0;
    }

    private String searchReferenceByAsin(String asin) {
        String clearedAsin = asin.trim().replace("\u200e", "");
        StringBuilder reference = new StringBuilder();
        Document searchDoc = ParserUtil.getHTMLDocument("https://www.amazon.com/s?k=asin+" + clearedAsin);
//        List<Element> elementList = searchDoc.select("div[data-asin]");
//        Optional<Element> elementOpt = elementList.stream().filter(element1 -> element1.attr("data-asin").equals(clearedAsin)).findFirst();
//        Optional.ofNullable(elementOpt.get()).ifPresent(
//                element -> reference.append("https://www.amazon.com").append(element.attr("href")));
        Optional.ofNullable(searchDoc.selectFirst("div[data-asin=\"" + clearedAsin + "\"] a")).ifPresent(
                element -> reference.append("https://www.amazon.com").append(element.attr("href")));
        return reference.toString();
    }

    public Product parseProductByASIN(String asin) {
        String reference = searchReferenceByAsin(asin);
        Product product = new Product();
        product.setReference(reference);

        //if there is no reference to product with entered ASIN, then return empty product
        //to handle this case on UI
        if (!reference.equals("")) {
            Document doc = ParserUtil.getHTMLDocument(reference);

            product.setAsin(Optional.ofNullable(doc.selectFirst("div[data-asin='" + asin + "']"))
                    .map(element -> element.attr("data-asin"))
                    .orElse(""));
            product.setTitle(Optional.ofNullable(doc.selectFirst("span[id=productTitle]"))
                    .map(element -> element.text())
                    .orElse(""));
            product.setRating(Optional.ofNullable(doc.selectFirst("span[id=acrPopover]"))
                    .map(element -> element.attr("title"))
                    .orElse(""));
            product.setRatingCount(Optional.ofNullable(doc.selectFirst("span[id=acrCustomerReviewText]"))
                    .map(element -> element.attr("title"))
                    .orElse(""));
            product.setPrice(Optional.ofNullable(doc.selectFirst("div[id=corePriceDisplay_desktop_feature_div] span.a-offscreen"))
                    .map(element -> element.attr("title"))
                    .orElse(""));
            product.setDescription(Optional.ofNullable(doc.selectFirst("div[id=productDescription] p span"))
                    .map(element -> element.attr("title"))
                    .orElse(""));
        }
        return product;
    }

    public Product updateProduct(String product_id) {
        Product product = getProductById(product_id);
        Product updatedProduct = parseProductByASIN(product.getAsin());
        BeanUtils.copyProperties(updatedProduct, product, "product_id", "reviews");
        return save(product);
    }

    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    public Product getProductById(String product_id) {
        return getAllProduct().stream()
                .filter(product -> product.getProduct_id().equals(Long.valueOf(product_id)))
                .findFirst().get();
    }

}
