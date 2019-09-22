package kz.iitu.jd3.bookcatalog;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rating {

    public Rating() {
    }

    public Rating(String bookId, Integer rating) {
        this.bookId = bookId;
        this.rating = rating;
    }

    private String bookId;
    private Integer rating; // 0 to 5
}
