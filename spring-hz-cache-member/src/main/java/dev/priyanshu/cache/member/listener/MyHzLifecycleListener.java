package dev.priyanshu.cache.member.listener;

import com.hazelcast.core.LifecycleEvent;
import com.hazelcast.core.LifecycleListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHzLifecycleListener implements LifecycleListener {

    @Override
    public void stateChanged(LifecycleEvent event){
        log.info("Life cycle state :: {}", event.getState());
    }

}