package dev.priyanshu.cache.member;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HzMember implements CommandLineRunner {

    /* Configuring Hazelcast manually and not relying on spring boot

    Negative matches:
    -----------------

       HazelcastClientInstanceConfiguration:
          Did not match:
             - @ConditionalOnBean (types: org.springframework.boot.hazelcast.autoconfigure.HazelcastConnectionDetails; SearchStrategy: all) did not find any beans of type org.springframework.boot.hazelcast.autoconfigure.HazelcastConnectionDetails (OnBeanCondition)

       HazelcastConnectionDetailsConfiguration.HazelcastClientConfigConfiguration:
          Did not match:
             - @ConditionalOnSingleCandidate (types: com.hazelcast.client.config.ClientConfig; SearchStrategy: all) did not find any beans (OnBeanCondition)

       HazelcastConnectionDetailsConfiguration.HazelcastClientConfigFileConfiguration:
          Did not match:
             - ResourceCondition (Hazelcast) did not find resources 'file:./hazelcast-client.xml', 'classpath:/hazelcast-client.xml', 'file:./hazelcast-client.yaml', 'classpath:/hazelcast-client.yaml', 'file:./hazelcast-client.yml', 'classpath:/hazelcast-client.yml' (HazelcastClientConfigAvailableCondition)

       HazelcastHealthContributorAutoConfiguration:
          Did not match:
             - @ConditionalOnClass did not find required class 'org.springframework.boot.health.autoconfigure.contributor.ConditionalOnEnabledHealthIndicator' (OnClassCondition)

       HazelcastJpaDependencyAutoConfiguration:
          Did not match:
             - @ConditionalOnClass did not find required class 'org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean' (OnClassCondition)

       HazelcastServerConfiguration.HazelcastServerConfigConfiguration:
          Did not match:
             - @ConditionalOnSingleCandidate (types: com.hazelcast.config.Config; SearchStrategy: all) did not find any beans (OnBeanCondition)

       HazelcastServerConfiguration.HazelcastServerConfigFileConfiguration:
          Did not match:
             - ResourceCondition (Hazelcast) did not find resources 'file:./hazelcast.xml', 'classpath:/hazelcast.xml', 'file:./hazelcast.yaml', 'classpath:/hazelcast.yaml', 'file:./hazelcast.yml', 'classpath:/hazelcast.yml' (HazelcastServerConfiguration.ConfigAvailableCondition)

       HazelcastServerConfiguration.SpringManagedContextHazelcastConfigCustomizerConfiguration:
          Did not match:
             - @ConditionalOnClass did not find required class 'com.hazelcast.spring.context.SpringManagedContext' (OnClassCondition)


     */

    @Autowired
    HazelcastInstance hzInstance;


    @Override
    public void run(String... args) throws Exception {
        HazelcastInstance hz = hzInstance;
        log.info("{} groupname {}",hz.getName(),  hz.getConfig().getClusterName());

        IMap<String, String> testMap = hz.getMap("test-map");
        testMap.put("name", "Priyanshu");
        testMap.put("city", "Chennai");

        log.info("Map data: {}", testMap);
    }
}
