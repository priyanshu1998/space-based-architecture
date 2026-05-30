package dev.priyanshu.cache.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.hazelcast.core.HazelcastInstance;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(HzMemberExtension.class)
@SpringBootTest
class HzClientIntegrationTest {

  private static final String TOPIC_NAME = "my-topic";

  @Autowired private HzClient hzClient;

  @Test
  void contextLoads() {
    assertThat(hzClient).isNotNull();
  }

  @Test
  void testPublishSendsMessageToTopic(HazelcastInstance member) throws Exception {
    List<String> receivedMessages = new CopyOnWriteArrayList<>();
    CountDownLatch latch = new CountDownLatch(1);

    member
        .getTopic(TOPIC_NAME)
        .addMessageListener(
            message -> {
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
  void testSubscribeReceivesPublishedMessage(HazelcastInstance member) {
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
      member.getTopic(TOPIC_NAME).publish(testMessage);

      await()
          .atMost(Duration.ofSeconds(5))
          .untilAsserted(
              () ->
                  assertThat(listAppender.list)
                      .extracting(ILoggingEvent::getFormattedMessage)
                      .anyMatch(
                          msg -> msg.contains("Received message") && msg.contains(testMessage)));
    } finally {
      hzClientLogger.detachAppender(listAppender);
    }
  }

  @Test
  void TestRingBufferCreation() {
    var productRingbuffer = hzClient.getHzRingBuffer(HzClient.HzConstants.EXAMPLE_RING_BUFFER);
    Assertions.assertNotNull(productRingbuffer);
  }
}
