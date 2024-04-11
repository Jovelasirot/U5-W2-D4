package jovelAsirot.U5W2D4.controllers;

import jovelAsirot.U5W2D4.entities.BlogPost;
import jovelAsirot.U5W2D4.payloads.BlogPostPayLoad;
import jovelAsirot.U5W2D4.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog/post")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost saveBlog(@RequestBody BlogPostPayLoad payload) {
        return this.blogPostService.save(payload);
    }

    @GetMapping
    public Page<BlogPost> getAllBlogPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.blogPostService.getAll(page, size, sortBy);
    }

    @GetMapping("{blogId}")
    public BlogPost getSingleBlog(@PathVariable Long blogId) {
        return this.blogPostService.findById(blogId);
    }

    @PutMapping("{blogId}")
    public BlogPost updateBlog(@PathVariable Long blogId, @RequestBody BlogPost blogBody) {
        return this.blogPostService.updateById(blogId, blogBody);
    }

    @DeleteMapping("{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long blogId) {
        this.blogPostService.deleteById(blogId);
    }
}
