package kz.iitu.jd3.bookcatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalog")
public class BookCatalogApi {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{userId}")
    public List<BookCatalog> getAllBooks(
            @PathVariable String userId) {



        // get all books by userId
        UserBook  userBook = restTemplate.getForObject(
                "http://localhost:8082/book/info/" + userId,
                UserBook.class);

        // get ratings for each book in a list
//        userBook.getUserBooks().stream().map(book -> {
//
//        }).collect(Collectors.toList());
        List<BookCatalog> bookCatalogList = new ArrayList<>();

        for (Book book : userBook.getUserBooks()) {
            Rating bookRating = restTemplate.getForObject(
                    "http://localhost:8083/rating/" + book.getId(),
                    Rating.class);

            bookCatalogList.add(new BookCatalog(book.getTitle(),
                    book.getAuthor(), bookRating.getRating()));
        }

        return bookCatalogList;
    }
}
