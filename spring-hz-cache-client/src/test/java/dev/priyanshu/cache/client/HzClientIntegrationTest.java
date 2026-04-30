package dev.priyanshu.cache.client;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
class HzClientIntegrationTest {

    /*
     * An embedded Hazelcast member is started in a static initializer so that it is
     * running before the Spring context is created. HzClient's own static initializer
     * calls HazelcastClient.newHazelcastClient(), which needs to find a live member
     * (localhost:5701 by default). Shutting the member down in @AfterAll keeps the
     * test JVM clean.
     */
    private static final HazelcastInstance member;

    static {
        member = Hazelcast.newHazelcastInstance(new Config());
    }

    @Autowired
    private HzClient hzClient;

    @AfterAll
    static void tearDown() {
        member.shutdown();
    }

    @Test
    void contextLoads() {
        assertThat(hzClient).isNotNull();
    }

    @Test
    void testPublishSendsMessageToTopic() throws Exception {
        List<String> receivedMessages = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        member.getTopic("my-topic").addMessageListener(message -> {
            receivedMessages.add((String) message.getMessageObject());
            latch.countDown();
        });

        hzClient.run("publish");

        assertThat(latch.await(5, TimeUnit.SECONDS))
                .as("Timed out waiting for published message")
                .isTrue();
        assertThat(receivedMessages).contains("Hello from client");
    }

    @Test
    void testSubscribeReceivesPublishedMessage() {
        Logger hzClientLogger = (Logger) LoggerFactory.getLogger(HzClient.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        hzClientLogger.addAppender(listAppender);

        try {
            /*
             * The subscriber for "my-topic" is already registered by the
             * CommandLineRunner callback that Spring Boot invokes at startup
             * (run() with no args). Publishing from the embedded member
             * exercises that listener end-to-end.
             */
            String testMessage = "Integration test message";
            member.getTopic("my-topic").publish(testMessage);

            await().atMost(Duration.ofSeconds(5))
                    .untilAsserted(() ->
                            assertThat(listAppender.list)
                                    .extracting(ILoggingEvent::getFormattedMessage)
                                    .anyMatch(msg -> msg.contains("Received message") && msg.contains(testMessage))
                    );
        } finally {
            hzClientLogger.detachAppender(listAppender);
        }
    }
}
