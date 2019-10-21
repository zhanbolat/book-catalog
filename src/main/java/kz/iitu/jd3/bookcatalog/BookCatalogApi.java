package kz.iitu.jd3.bookcatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class BookCatalogApi {

    @Autowired
    private BookInformationService bookInformationService;
    @Autowired
    private RatingService ratingService;

    @GetMapping("/{userId}")
    public List<BookCatalog> getAllBooks(
            @PathVariable String userId) {



        // get all books by userId
        UserBook  userBook = bookInformationService.getUserBooks(userId);

        List<BookCatalog> bookCatalogList = new ArrayList<>();
        for (Book book : userBook.getUserBooks()) {
            Rating bookRating = ratingService.getBookRating(book.getId());

            bookCatalogList.add(new BookCatalog(book.getTitle(),
                    book.getAuthor(), bookRating.getRating()));
        }

        return bookCatalogList;
    }


}
