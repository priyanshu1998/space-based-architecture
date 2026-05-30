package dev.priyanshu.cache.client;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.ringbuffer.Ringbuffer;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

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

    private class Subject {
        public void publish(String topicName, String msg) {
            var topic = hzInstance.getTopic(topicName);
            topic.publish(msg);
        }
    }

    private class Observer {
        public void subscribe(String topicName) {
            var topic = hzInstance.getTopic(topicName);
            topic.addMessageListener(message -> log.info("Received message :: {}", message.getMessageObject()));
        }
    }

    @Override
    public void run(String... args) throws Exception {
        var hz = hzInstance;
        log.info("hz {} group name :: {}", hz.getName(), hz.getConfig().getClusterName());

        productRingbuffer = getHzRingBuffer(HzConstants.EXAMPLE_RING_BUFFER);
        log.info("Ringbuffer::productRingbuffer referenced");

        if(args.length > 0 && args[0].equalsIgnoreCase("publish")){
            new Subject().publish("my-topic", "Hello from client");
        } else {
            new Observer().subscribe("my-topic");
        }
    }



}
