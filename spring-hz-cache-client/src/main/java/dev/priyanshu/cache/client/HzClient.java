package dev.priyanshu.cache.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.topic.ITopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HzClient implements CommandLineRunner {

    private static final HazelcastInstance hz;

    static {
        hz = HazelcastClient.newHazelcastClient();
    }

    private static class Subject {
        public void publish(String topicName, String msg) {
            var topic = hz.getTopic(topicName);
            topic.publish(msg);
        }
    }

    private static class Observer {
        public void subscribe(String topicName) {
            var topic = hz.getTopic(topicName);
            topic.addMessageListener(message -> log.info("Received message :: {}", message.getMessageObject()));
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("hz {} group name :: {}", hz.getName(), hz.getConfig().getClusterName());

        if(args.length > 0 && args[0].equalsIgnoreCase("publish")){
            new Subject().publish("my-topic", "Hello from client");
        } else {
            new Observer().subscribe("my-topic");
        }
    }



}
