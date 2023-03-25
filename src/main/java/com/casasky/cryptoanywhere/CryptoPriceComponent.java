package com.casasky.cryptoanywhere;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.casasky.cryptoanywhere.CryptoListing.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class CryptoPriceComponent {
    private final CryptoListing cryptoListing;

    private final CryptoApiClient cryptoApiClient;

    private final CryptoPriceRepository cryptoPriceRepository;

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void updatePrices() {
        log.info(cryptoListing.getListing().toString());
        cryptoPriceRepository.saveAll(cryptoListing.getListing().stream()
                .map(this::price)
                .toList());
    }

    private CryptoPrice price(Crypto crypto) {
        return new CryptoPrice(crypto.name(), cryptoApiClient.price(crypto));
    }

}
