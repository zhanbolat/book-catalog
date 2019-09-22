package kz.iitu.jd3.bookcatalog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCatalog {

    public BookCatalog() {
    }

    public BookCatalog(String title, String author, Integer rating) {
        this.title = title;
        this.author = author;
        this.rating = rating;
    }

    private String title;
    private String author;
    private Integer rating;
}
