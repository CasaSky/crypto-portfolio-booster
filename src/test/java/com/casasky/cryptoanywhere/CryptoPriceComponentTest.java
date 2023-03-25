package com.casasky.cryptoanywhere;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CryptoPriceComponentTest {

    @MockBean
    SchedulingConfig schedulingConfig;

    @Autowired
    CryptoPriceRepository cryptoPriceRepository;

    @Autowired
    CryptoPriceComponent sut;

    @Test
    void updatePrices() {
        sut.updatePrices();

        assertThat(cryptoPriceRepository.findAll()).extracting(CryptoPrice::getCurrency).containsExactly("bitcoin");
        assertThat(cryptoPriceRepository.findAll()).extracting(CryptoPrice::getPrice).doesNotContainNull();
    }
}