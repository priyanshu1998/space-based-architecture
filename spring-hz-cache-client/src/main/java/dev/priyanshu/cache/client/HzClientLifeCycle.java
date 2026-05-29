package dev.priyanshu.cache.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class HzClientLifeCycle {
  private ClientConfig getClientConfig() {
    var clientConfig = new ClientConfig();
    var networkConfig = clientConfig.getNetworkConfig();

    // Member is configured with port 5701, portAutoIncrement=true, portCount=20
    // So possible ports are 5701 to 5720
    int basePort = 5701;
    int portCount = 20;
    java.util.stream.IntStream.range(0, portCount)
        .mapToObj(i -> "localhost:" + (basePort + i))
        .forEach(networkConfig::addAddress);

    return clientConfig;
  }

  @Lazy
  @Bean
  public HazelcastInstance hazelcastInstance() {
    var clientConfig = getClientConfig();
    return HazelcastClient.newHazelcastClient(clientConfig);
  }
}
