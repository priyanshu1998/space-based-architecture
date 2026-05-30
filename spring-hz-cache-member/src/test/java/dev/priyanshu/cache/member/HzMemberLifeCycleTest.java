package dev.priyanshu.cache.member;

import static org.mockito.ArgumentMatchers.any;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import java.util.EventListener;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = HzMemberLifeCycle.class)
@MockitoSpyBean(types = Config.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("IT")
class HzMemberLifeCycleTest {

  @Autowired HzMemberLifeCycle hzMemberLifeCycle;

  @Autowired Config config;

  @Autowired List<EventListener> listeners;

  @Test
  void context() {}

  @Test
  void hazelcastInstance_shouldAddListenerToConfig_givenListenersBean() {
    try (MockedStatic<Hazelcast> hazelcastMockedStatic = Mockito.mockStatic(Hazelcast.class)) {
      hazelcastMockedStatic
          .when(() -> Hazelcast.newHazelcastInstance(config))
          .thenReturn(Mockito.mock(HazelcastInstance.class));
      Assertions.assertNotNull(hzMemberLifeCycle.hazelcastInstance(config, listeners));
      Mockito.verify(config, Mockito.times(listeners.size())).addListenerConfig(any());
    }
  }
}
