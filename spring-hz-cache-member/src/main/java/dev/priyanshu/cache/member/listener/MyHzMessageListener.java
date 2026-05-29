package dev.priyanshu.cache.member.listener;

import com.hazelcast.topic.Message;
import com.hazelcast.topic.MessageListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHzMessageListener implements MessageListener<String> {

  @Override
  public void onMessage(Message<String> message) {
    log.info("Received : {}", message.getMessageObject());
  }
}
