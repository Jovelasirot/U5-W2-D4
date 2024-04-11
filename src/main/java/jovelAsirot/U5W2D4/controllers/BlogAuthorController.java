package jovelAsirot.U5W2D4.controllers;

import jovelAsirot.U5W2D4.entities.BlogAuthor;
import jovelAsirot.U5W2D4.exceptions.BadRequestException;
import jovelAsirot.U5W2D4.payloads.BlogAuthorDTO;
import jovelAsirot.U5W2D4.payloads.BlogAuthorResponseDTO;
import jovelAsirot.U5W2D4.services.BlogAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/blog/author")
public class BlogAuthorController {
    @Autowired
    private BlogAuthorService blogAuthorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
  public BlogAuthorResponseDTO saveAuthor(@RequestBody @Validated BlogAuthorDTO payload, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return new BlogAuthorResponseDTO(this.blogAuthorService.save(payload).getId());
    }

    @PostMapping("/avatar/upload/{authorId}")
    public String uploadAvatar(@PathVariable Long authorId, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.blogAuthorService.uploadImage(authorId, image);
    }

    @GetMapping
    public Page<BlogAuthor> getAllAuthors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.blogAuthorService.getAll(page, size, sortBy);
    }

    @GetMapping("{authorId}")
    public BlogAuthor getSingleAuthor(@PathVariable Long authorId) {
        return this.blogAuthorService.findById(authorId);
    }

    @PutMapping("{authorId}")
    public BlogAuthor updateAuthor(@PathVariable Long authorId, @RequestBody BlogAuthor authorBody) {
        return this.blogAuthorService.updateById(authorId, authorBody);
    }

    @DeleteMapping("{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long authorId) {
        this.blogAuthorService.deleteById(authorId);
    }
}
