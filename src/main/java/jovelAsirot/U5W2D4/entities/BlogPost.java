package jovelAsirot.U5W2D4.entities;

import jakarta.persistence.*;
import jovelAsirot.U5W2D4.enums.Category;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String cover;

    private String content;

    private int readingTime;

    @ManyToMany
    @JoinTable(
            name = "blog_post_authors",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<BlogAuthor> authors = new HashSet<>();

}
