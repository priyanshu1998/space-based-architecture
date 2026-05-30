package dev.priyanshu.cache.member;

import com.hazelcast.config.Config;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import dev.priyanshu.cache.member.listener.MyHzClientListener;
import dev.priyanshu.cache.member.listener.MyHzLifecycleListener;
import dev.priyanshu.cache.member.listener.MyHzMembershipListener;
import java.util.EventListener;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
class HzMemberLifeCycle {

  @Bean("hzConfig")
  Config getConfig() {
    var config = new Config();
    config.setClusterName("dev");

    config.getNetworkConfig().setPort(5701).setPortAutoIncrement(true).setPortCount(20);

    return config;
  }

  private void addAllListener(Config config, List<EventListener> listeners) {
    Consumer<List<EventListener>> addListeners =
        (listenerList) -> {
          for (var listener : listeners) {
            config.addListenerConfig(new ListenerConfig(listener));
          }
        };
    addListeners.accept(listeners);
  }

  @Bean("hzListeners")
  List<EventListener> getListeners() {
    return List.of(
        new MyHzLifecycleListener(), new MyHzMembershipListener(), new MyHzClientListener());
  }

  @Bean
  @Profile("!IT")
  public HazelcastInstance hazelcastInstance(
      @Qualifier("hzConfig") Config config,
      @Qualifier("hzListeners") List<EventListener> listeners) {
    addAllListener(config, listeners);
    return Hazelcast.newHazelcastInstance(config);
  }
}
