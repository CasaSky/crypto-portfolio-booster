package com.casasky.cryptoanywhere;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CryptoPriceNotification {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final SnsClient snsClient;

    public void subscribeTo(String email, String topicName) {
        SnsResponseMetadata subscribeResponse = topicArn(topicName).map(topicArn -> subscribe(email, topicArn))
                .map(SnsResponse::responseMetadata)
                .orElseThrow();
        log.info(subscribeResponse.toString());
    }

    public void unsubscribeTo(String email, String topicName) {
        SnsResponseMetadata unsubscribeResponse = topicArn(topicName).map(topicArn -> unsubscribe(email, topicArn))
                .map(SnsResponse::responseMetadata)
                .orElseThrow();
        log.info(unsubscribeResponse.toString());
    }

    private UnsubscribeResponse unsubscribe(String email, String topicArn) {
        return snsClient.listSubscriptionsByTopic(listSubscriptionsByTopicRequest(topicArn))
                .subscriptions()
                .stream()
                .filter(subscription -> subscription.endpoint().equals(email)).map(Subscription::subscriptionArn)
                .findAny()
                .map(CryptoPriceNotification::unsubscribeRequest)
                .map(snsClient::unsubscribe)
                .orElseThrow();
    }

    private Optional<String> topicArn(String topicName) {
        return snsClient.listTopics().topics()
                .stream()
                .map(Topic::topicArn)
                .filter(topicArn -> topicArn.contains(topicName))
                .findAny();
    }

    private SubscribeResponse subscribe(String email, String topicArn) {
        return snsClient.subscribe(SubscribeRequest.builder()
                .protocol("email-json")
                .endpoint(email)
                .returnSubscriptionArn(true)
                .topicArn(topicArn)
                .attributes(Map.of("FilterPolicy", toJson()))
                .build());
    }

    @SneakyThrows
    private static String toJson() {
        return MAPPER.writeValueAsString(new FilterPolicy(List.of("mail@talaltabia.com")));
    }

    private static UnsubscribeRequest unsubscribeRequest(String subscriptionArn) {
        return UnsubscribeRequest.builder()
                .subscriptionArn(subscriptionArn)
                .build();
    }

    private static ListSubscriptionsByTopicRequest listSubscriptionsByTopicRequest(String topicArn) {
        return ListSubscriptionsByTopicRequest.builder().topicArn(topicArn).build();
    }

}
