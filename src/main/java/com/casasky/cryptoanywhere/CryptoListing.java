package com.casasky.cryptoanywhere;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource(name = "props", value = "classpath:crypto-listing.properties")
@ConfigurationProperties(prefix = "crypto")
@Getter
@Setter
public class CryptoListing {

    private List<Crypto> listing;

    public record Crypto(String name, String url) {}

}
