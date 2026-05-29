package dev.priyanshu.cache.client;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.topic.ITopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HzClient implements CommandLineRunner {

  private static class HzConstants {
    // Maps
    public static String TEST_MAP = "test-map";

    // Topic
    public static String TEST_TOPIC = "test-topic";
  }

  private final HazelcastInstance hzInstance;

  public static IMap<String, String> testMap;
  public static ITopic<String> testTopic;

  public HzClient(HazelcastInstance hzInstance) {
    this.hzInstance = hzInstance;
  }

  @Override
  public void run(String... args) throws Exception {
    var hz = hzInstance;
    log.info("hz {} group name :: {}", hz.getName(), hz.getConfig().getClusterName());

    testMap = hz.getMap(HzConstants.TEST_MAP);
    log.info("HzMember::Map::testMap referenced");

    testTopic = hz.getTopic(HzConstants.TEST_TOPIC);
    log.info("HzMember::Topic::testTopic referenced");
  }
}
