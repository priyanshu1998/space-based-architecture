package dev.priyanshu.cache.client;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * JUnit 5 extension that starts an embedded Hazelcast member before any tests in
 * the annotated class run and shuts it down once all tests have completed.
 *
 * <p>Test methods that need direct access to the member can declare a
 * {@link HazelcastInstance} parameter and it will be resolved automatically:
 * <pre>{@code
 * @Test
 * void myTest(HazelcastInstance member) { ... }
 * }</pre>
 */
public class HzMemberExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(HzMemberExtension.class);

    private static final String MEMBER_KEY = "hazelcastMember";

    @Override
    public void beforeAll(ExtensionContext context) {
        HazelcastInstance member = Hazelcast.newHazelcastInstance(new Config());
        context.getStore(NAMESPACE).put(MEMBER_KEY, member);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        HazelcastInstance member = context.getStore(NAMESPACE)
                .remove(MEMBER_KEY, HazelcastInstance.class);
        if (member != null) {
            member.shutdown();
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return HazelcastInstance.class.isAssignableFrom(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        // The member is stored in the class-level context; method-level contexts have it as parent.
        return extensionContext.getParent()
                .orElse(extensionContext)
                .getStore(NAMESPACE)
                .get(MEMBER_KEY, HazelcastInstance.class);
    }
}
