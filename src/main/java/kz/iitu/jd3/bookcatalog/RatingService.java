package kz.iitu.jd3.bookcatalog;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RatingService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(
            fallbackMethod = "getBookRatingFallback",
            threadPoolKey = "getBookRating",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="100"),
                    @HystrixProperty(name="maximumSize", value="120"),
                    @HystrixProperty(name="maxQueueSize", value="50"),
                    @HystrixProperty(name="allowMaximumSizeToDivergeFromCoreSize", value="true"),
            })
    public Rating getBookRating(String bookId) {
        return restTemplate.getForObject(
                "http://book-ratings-service/rating/" + bookId,
                Rating.class);
    }
    public Rating getBookRatingFallback(String bookId) {
        return new Rating(bookId, 0);
    }
}
