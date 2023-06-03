package letscode.sarafan.repo;

import letscode.sarafan.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByAsin(String asin);
    @Transactional
    void deleteProductById(Long id);
}
