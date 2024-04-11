package jovelAsirot.U5W2D4.payloads;

import jovelAsirot.U5W2D4.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class BlogPostPayLoad {
    private Category category;
    private String cover;
    private String content;
    private int readingTime;
    private Set<Long> authorIds;
}
