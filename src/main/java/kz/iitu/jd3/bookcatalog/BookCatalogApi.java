package kz.iitu.jd3.bookcatalog;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod = "getAllBooksFallback")
    public List<BookCatalog> getAllBooks(
            @PathVariable String userId) {



        // get all books by userId
        UserBook  userBook = restTemplate.getForObject(
                "http://book-info-service/book/info/" + userId,
                UserBook.class);

        // get ratings for each book in a list
//        userBook.getUserBooks().stream().map(book -> {
//
//        }).collect(Collectors.toList());
        List<BookCatalog> bookCatalogList = new ArrayList<>();

        for (Book book : userBook.getUserBooks()) {
            Rating bookRating = restTemplate.getForObject(
                    "http://book-ratings-service/rating/" + book.getId(),
                    Rating.class);

            bookCatalogList.add(new BookCatalog(book.getTitle(),
                    book.getAuthor(), bookRating.getRating()));
        }

        return bookCatalogList;
    }

    public List<BookCatalog> getAllBooksFallback(
            @PathVariable String userId) {

        List<BookCatalog> bookCatalogList = new ArrayList<>();
        bookCatalogList.add(new BookCatalog("Not available", "Not available", -1));

        return bookCatalogList;
    }
}
