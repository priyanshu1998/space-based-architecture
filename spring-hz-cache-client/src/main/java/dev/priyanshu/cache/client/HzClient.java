package dev.priyanshu.cache.client;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.ringbuffer.Ringbuffer;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HzClient implements CommandLineRunner {

  private static class HzConstants {

    // Ring
    public static String EXAMPLE_RING_BUFFER = "example-ringbuffer";
  }

  private final HazelcastInstance hzInstance;

  public static Ringbuffer<String> productRingbuffer;

  public HzClient(HazelcastInstance hzInstance) {
    this.hzInstance = hzInstance;
  }

  public <K> Ringbuffer<K> getHzRingBuffer(String name) {
    Supplier<Ringbuffer<K>> supplier = () -> hzInstance.getRingbuffer(name);
    return supplier.get();
  }

  @Override
  public void run(String... args) throws Exception {
    var hz = hzInstance;
    log.info("hz {} group name :: {}", hz.getName(), hz.getConfig().getClusterName());

    productRingbuffer = getHzRingBuffer(HzConstants.EXAMPLE_RING_BUFFER);
    log.info("Ringbuffer::productRingbuffer referenced");
  }
}
