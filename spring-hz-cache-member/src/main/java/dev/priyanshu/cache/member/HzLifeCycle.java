package dev.priyanshu.cache.member;

import com.hazelcast.config.Config;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import dev.priyanshu.cache.member.listener.MyHzClientListener;
import dev.priyanshu.cache.member.listener.MyHzLifecycleListener;
import dev.priyanshu.cache.member.listener.MyHzMembershipListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class HzLifeCycle {

    private Config getConfig(){
        return new Config();
    }


    @Bean
    public HazelcastInstance hazelcastInstance(){
        var config = getConfig();
        config.addListenerConfig(new ListenerConfig(new MyHzLifecycleListener()));
        config.addListenerConfig(new ListenerConfig(new MyHzMembershipListener()));
        config.addListenerConfig(new ListenerConfig(new MyHzClientListener()));

        return Hazelcast.newHazelcastInstance(config);
    }


}
