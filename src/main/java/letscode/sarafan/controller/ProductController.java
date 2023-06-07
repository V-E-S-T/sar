package letscode.sarafan.controller;

import letscode.sarafan.domain.Product;
import letscode.sarafan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public List<Product> listProducts(){
        return productService.getAllProduct();
    }

    @GetMapping("{product_id}")
    public Product getProduct(@PathVariable("product_id") Product product){
        return product;
    }

    @PostMapping
    public Product create(@RequestBody String asin){
        Product newProduct = productService.parseProductByASIN(asin);
        if (newProduct.getReference().equals("")){
            return newProduct;
        }
        return productService.save(newProduct);
    }
    @PutMapping("{product_id}")
    public Product update(@PathVariable String product_id){
        return productService.updateProduct(product_id);
    }

    @DeleteMapping("{asin}")
    public boolean delete(@PathVariable String asin){
        return productService.deleteByAsin(asin);
    }
}
