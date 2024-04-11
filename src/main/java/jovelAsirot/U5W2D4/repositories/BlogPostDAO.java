package jovelAsirot.U5W2D4.repositories;

import jovelAsirot.U5W2D4.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostDAO extends JpaRepository<BlogPost, Long> {
}
