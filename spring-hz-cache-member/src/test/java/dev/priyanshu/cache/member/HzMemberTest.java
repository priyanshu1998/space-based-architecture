package dev.priyanshu.cache.member;

import static org.mockito.ArgumentMatchers.anyString;

import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HzMemberTest {
  private final HazelcastInstance hzInstanceMock;
  private final HzMember hzMember;

  HzMemberTest() {
    hzInstanceMock = Mockito.mock();
    hzMember = new HzMember(hzInstanceMock);
  }

  @Test
  void getHzMap_shouldReturnMap_givenNonNullName() {
    Mockito.when(hzInstanceMock.getMap(anyString())).thenReturn(Mockito.mock());
    hzMember.getHzMap("test-map");
    Mockito.verify(hzInstanceMock, Mockito.times(1)).getMap(anyString());
  }

  @Test
  void getHzTopic_shouldReturnTopic_givenNonNullName() {
    Mockito.when(hzInstanceMock.getTopic(anyString())).thenReturn(Mockito.mock());
    hzMember.getHzTopic("test-topic");
    Mockito.verify(hzInstanceMock, Mockito.times(1)).getTopic(anyString());
  }
}
