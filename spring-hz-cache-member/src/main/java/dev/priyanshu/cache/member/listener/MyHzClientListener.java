package dev.priyanshu.cache.member.listener;

import com.hazelcast.client.Client;
import com.hazelcast.client.ClientListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHzClientListener implements ClientListener {
  @Override
  public void clientConnected(Client client) {
    log.info("Client connected :: {} - {}", client.getUuid(), client.getName());
  }

  @Override
  public void clientDisconnected(Client client) {
    log.info("Client disconnected :: {} - {}", client.getUuid(), client.getName());
  }
}
