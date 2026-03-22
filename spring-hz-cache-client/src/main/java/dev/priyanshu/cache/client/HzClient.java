package dev.priyanshu.cache.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HzClient implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        HazelcastInstance hz = HazelcastClient.newHazelcastClient();

        log.info("hz {} group name :: {}", hz.getName(), hz.getConfig().getClusterName());
    }
}
