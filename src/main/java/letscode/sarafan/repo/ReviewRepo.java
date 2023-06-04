package letscode.sarafan.repo;

import letscode.sarafan.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}
