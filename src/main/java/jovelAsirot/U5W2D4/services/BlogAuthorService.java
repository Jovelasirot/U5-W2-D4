package jovelAsirot.U5W2D4.services;

import jovelAsirot.U5W2D4.entities.BlogAuthor;
import jovelAsirot.U5W2D4.exceptions.NotFoundException;
import jovelAsirot.U5W2D4.payloads.BlogAuthorDTO;
import jovelAsirot.U5W2D4.repositories.BlogAuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class BlogAuthorService {

    @Autowired
    private BlogAuthorDAO baDAO;

    public Page<BlogAuthor> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.baDAO.findAll(pageable);
    }

    public BlogAuthor save(BlogAuthorDTO payload) {
        BlogAuthor newAuthor = new BlogAuthor(payload.name(),payload.surname(),payload.email(),payload.birthDate(),"https://ui-avatars.com/api/?name="+ payload.name() + "+" + payload.surname());
        return baDAO.save(newAuthor);
    }

    public BlogAuthor findById(Long authorId) {
        return this.baDAO.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));
    }

    public BlogAuthor updateById(Long authorId, BlogAuthor updatedAuthor) {
        BlogAuthor authorFound = this.findById(authorId);

        Random rdm = new Random();
        LocalDate today = LocalDate.now();

        String authorEmail = updatedAuthor.getName() + updatedAuthor.getSurname() + "@gmail.com";
        String authorAvatar = "https://ui-avatars.com/api/?name=" + updatedAuthor.getName() + "+" + updatedAuthor.getSurname();

        authorFound.setName(updatedAuthor.getName());
        authorFound.setSurname(updatedAuthor.getSurname());
        authorFound.setBirthDate(today.minusYears(rdm.nextInt(11, 20)));
        authorFound.setEmail(authorEmail);
        authorFound.setAvatar(authorAvatar);

        return this.baDAO.save(authorFound);
    }

    public void deleteById(Long authorId) {
        BlogAuthor blogAuthorFound = this.findById(authorId);
        this.baDAO.delete(blogAuthorFound);
    }
}
