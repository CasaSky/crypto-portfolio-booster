package com.casasky.cryptoanywhere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.services.sns.SnsClient;

@SpringBootTest
@ActiveProfiles("test")
abstract class BaseIntegrationTest {

    @MockBean
    SchedulingConfig schedulingConfig;

    @Autowired
    CryptoPriceComponent priceComponent;

    @Autowired
    CryptoPriceRepository cryptoPriceRepository;

    @Autowired
    CryptoPriceNotification cryptoPriceNotification;

    @Autowired
    SnsClient snsClient;

}
