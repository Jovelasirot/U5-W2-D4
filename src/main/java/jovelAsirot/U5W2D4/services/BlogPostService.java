package jovelAsirot.U5W2D4.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jovelAsirot.U5W2D4.entities.BlogAuthor;
import jovelAsirot.U5W2D4.entities.BlogPost;
import jovelAsirot.U5W2D4.payloads.BlogPostPayLoad;
import jovelAsirot.U5W2D4.enums.Category;
import jovelAsirot.U5W2D4.exceptions.NotFoundException;
import jovelAsirot.U5W2D4.repositories.BlogAuthorDAO;
import jovelAsirot.U5W2D4.repositories.BlogPostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostDAO bpDAO;

    @Autowired
    private BlogAuthorDAO baDAO;

    @Autowired
    private BlogAuthorService blogAuthorService;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<BlogPost> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.bpDAO.findAll(pageable);
    }

    private Set<BlogAuthor> fetchAuthorsRdm(int numOfAuthors) {
        Set<BlogAuthor> authors = new HashSet<>();
        List<BlogAuthor> authorList = baDAO.findAll();

        Collections.shuffle(authorList);

        List<BlogAuthor> randomAuthors = authorList.subList(0, Math.min(numOfAuthors, authorList.size()));

        for (BlogAuthor author : randomAuthors) {
            BlogAuthor blogAuthor = new BlogAuthor();
            blogAuthor.setId(author.getId());
            blogAuthor.setName(author.getName());
            blogAuthor.setSurname(author.getSurname());
            blogAuthor.setEmail(author.getEmail());
            blogAuthor.setAvatar(author.getAvatar());
            blogAuthor.setBirthDate(author.getBirthDate());
            authors.add(blogAuthor);
        }

        return authors;
    }

    private Set<BlogAuthor> fetchAuthorsSelected(BlogPostPayLoad payload) {
        Set<BlogAuthor> authors = new HashSet<>();

        Set<Long> authorIds = payload.getAuthorIds();

        for (Long authorId : authorIds) {
            BlogAuthor author = blogAuthorService.findById(authorId);
            if (author != null) {
                authors.add(author);
            } else {
                throw new NotFoundException(authorId);
            }
        }

        return authors;

    }


    public BlogPost save(BlogPostPayLoad payload) {
        Random rdm = new Random();
        Category[] categories = Category.values();

        BlogPost newBlogPost = new BlogPost();
        newBlogPost.setCategory(categories[rdm.nextInt(categories.length)]);
        newBlogPost.setCover(payload.getCover());
        newBlogPost.setContent(payload.getContent());
        newBlogPost.setReadingTime(payload.getReadingTime());

        Set<BlogAuthor> authors;
        if (payload.getAuthorIds() == null) {
            authors = fetchAuthorsRdm(rdm.nextInt(1, 4));
        } else {
            authors = fetchAuthorsSelected(payload);
        }

        newBlogPost.setAuthors(authors);
        return this.bpDAO.save(newBlogPost);
    }

    public String uploadImage(Long blogPostId, MultipartFile image) throws IOException {
       BlogPost blogPost = findById(blogPostId);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        blogPost.setCover(url);
        bpDAO.save(blogPost);
        return url;
    }

    public BlogPost findById(Long blogId) {
        return this.bpDAO.findById(blogId).orElseThrow(() -> new NotFoundException(blogId));
    }

    public BlogPost updateById(Long blogId, BlogPost updatedBlogPost) {
        BlogPost blogPostFound = this.findById(blogId);

        blogPostFound.setAuthors(updatedBlogPost.getAuthors());
        blogPostFound.setCategory(updatedBlogPost.getCategory());
        blogPostFound.setContent(updatedBlogPost.getContent());

        return this.bpDAO.save(blogPostFound);
    }

    public void deleteById(Long blogId) {
        BlogPost blogPostFound = this.findById(blogId);
        this.bpDAO.delete(blogPostFound);
    }
}
