package dev.priyanshu.cache.member.listener;

import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyHzMembershipListener implements MembershipListener {
    @Override
    public void memberAdded(MembershipEvent membershipEvent) {
        log.info("Member added :: {}", membershipEvent.getMember().getUuid());
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
        log.info("Member removed :: {}", membershipEvent.getMember().getUuid());
    }
}
