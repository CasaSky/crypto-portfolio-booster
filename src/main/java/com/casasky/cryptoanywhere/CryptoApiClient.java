package com.casasky.cryptoanywhere;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.String.format;

@Component
@Slf4j
public class CryptoApiClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    @RateLimiter(name = "crypto-api")
    public Long price(CryptoListing.Crypto crypto) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(new URI(crypto.url()))
                    .GET()
                    .build();
            String body = CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
            log.info(body);
            return MAPPER.readTree(body)
                    .at(format("/%s/eur", crypto.name()))
                    .asLong();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
