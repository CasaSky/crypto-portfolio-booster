package com.casasky.cryptoanywhere;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsResponse;
import software.amazon.awssdk.services.sns.model.Subscription;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class CryptoPriceNotificationTest {

    @Autowired
    CryptoPriceNotification cryptoPriceNotification;

    @Autowired
    SnsClient snsClient;

    @Test
    void subscribeAndUnsubscribe() {
        String email = "mail@talaltabia.com";
        cryptoPriceNotification.subscribeTo(email, "crypto-purchase");

        assertThat(snsClient.listSubscriptions())
                .extracting(ListSubscriptionsResponse::subscriptions)
                .asList()
                .satisfiesExactly(subscriptions ->
                        assertThat(subscriptions).isInstanceOfSatisfying(Subscription.class, s ->
                                assertThat(s).extracting(Subscription::endpoint)
                                        .isEqualTo(email)));

        cryptoPriceNotification.unsubscribeTo(email, "crypto-purchase");

        assertThat(snsClient.listSubscriptions())
                .extracting(ListSubscriptionsResponse::subscriptions)
                .asList()
                .isEmpty();
    }

}