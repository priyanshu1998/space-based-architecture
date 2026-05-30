package dev.priyanshu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.hazelcast.autoconfigure.HazelcastAutoConfiguration;

@SpringBootApplication(exclude = HazelcastAutoConfiguration.class)
public class SpringHzCacheClientApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringHzCacheClientApplication.class, args);
  }
}
