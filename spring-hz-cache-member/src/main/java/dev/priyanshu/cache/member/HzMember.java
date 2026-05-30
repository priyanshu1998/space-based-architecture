package dev.priyanshu.cache.member;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.hazelcast.topic.ITopic;
import dev.priyanshu.cache.member.listener.MyHzMessageListener;
import java.util.Map;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HzMember implements CommandLineRunner {

  public HzMember(HazelcastInstance hzInstance) {
    this.hzInstance = hzInstance;
  }

  private static class HzConstants {
    // Maps
    public static String TEST_MAP = "test-map";

    // Topic
    public static String TEST_TOPIC = "test-topic";

    // Ring
    public static String EXAMPLE_RING_BUFFER = "example-ringbuffer";
  }

  /* Configuring Hazelcast manually and not relying on spring boot

  Negative matches:
  -----------------

     HazelcastClientInstanceConfiguration:
        Did not match:
           - @ConditionalOnBean (types: org.springframework.boot.hazelcast.autoconfigure.HazelcastConnectionDetails; SearchStrategy: all) did not find any beans of type org.springframework.boot.hazelcast.autoconfigure.HazelcastConnectionDetails (OnBeanCondition)

     HazelcastConnectionDetailsConfiguration.HazelcastClientConfigConfiguration:
        Did not match:
           - @ConditionalOnSingleCandidate (types: com.hazelcast.client.config.ClientConfig; SearchStrategy: all) did not find any beans (OnBeanCondition)

     HazelcastConnectionDetailsConfiguration.HazelcastClientConfigFileConfiguration:
        Did not match:
           - ResourceCondition (Hazelcast) did not find resources 'file:./hazelcast-client.xml', 'classpath:/hazelcast-client.xml', 'file:./hazelcast-client.yaml', 'classpath:/hazelcast-client.yaml', 'file:./hazelcast-client.yml', 'classpath:/hazelcast-client.yml' (HazelcastClientConfigAvailableCondition)

     HazelcastHealthContributorAutoConfiguration:
        Did not match:
           - @ConditionalOnClass did not find required class 'org.springframework.boot.health.autoconfigure.contributor.ConditionalOnEnabledHealthIndicator' (OnClassCondition)

     HazelcastJpaDependencyAutoConfiguration:
        Did not match:
           - @ConditionalOnClass did not find required class 'org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean' (OnClassCondition)

     HazelcastServerConfiguration.HazelcastServerConfigConfiguration:
        Did not match:
           - @ConditionalOnSingleCandidate (types: com.hazelcast.config.Config; SearchStrategy: all) did not find any beans (OnBeanCondition)

     HazelcastServerConfiguration.HazelcastServerConfigFileConfiguration:
        Did not match:
           - ResourceCondition (Hazelcast) did not find resources 'file:./hazelcast.xml', 'classpath:/hazelcast.xml', 'file:./hazelcast.yaml', 'classpath:/hazelcast.yaml', 'file:./hazelcast.yml', 'classpath:/hazelcast.yml' (HazelcastServerConfiguration.ConfigAvailableCondition)

     HazelcastServerConfiguration.SpringManagedContextHazelcastConfigCustomizerConfiguration:
        Did not match:
           - @ConditionalOnClass did not find required class 'com.hazelcast.spring.context.SpringManagedContext' (OnClassCondition)

   */

  private final HazelcastInstance hzInstance;

  public static Map<String, String> testMap;
  public static ITopic<String> testTopic;
  public static Ringbuffer<String> exampleRingbuffer;

  public <K, V> Map<K, V> getHzMap(String name) {
    Supplier<IMap<K, V>> supplier = () -> hzInstance.getMap(name);
    return supplier.get();
  }

  public <K> ITopic<K> getHzTopic(String name) {
    Supplier<ITopic<K>> supplier = () -> hzInstance.getTopic(name);
    return supplier.get();
  }

  public <K> Ringbuffer<K> getHzRingBuffer(String name) {
    Supplier<Ringbuffer<K>> supplier = () -> hzInstance.getRingbuffer(name);
    return supplier.get();
  }

  @Override
  public void run(String... args) throws Exception {
    HazelcastInstance hz = hzInstance;
    log.info("{} group name {}", hz.getName(), hz.getConfig().getClusterName());

    testMap = getHzMap(HzConstants.TEST_MAP);
    log.info("Map::testMap referenced");

    testTopic = getHzTopic(HzConstants.TEST_TOPIC);
    log.info("Topic::testTopic referenced");

    exampleRingbuffer = getHzRingBuffer(HzConstants.EXAMPLE_RING_BUFFER);
    log.info("Topic::exampleRingbuffer referenced");

    testTopic.addMessageListener(new MyHzMessageListener());
  }
}
