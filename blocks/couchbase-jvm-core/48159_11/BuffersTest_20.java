package com.couchbase.client.core.util;

import com.couchbase.client.core.RequestFactory;
import org.mockito.ArgumentMatcher;

public class Matchers {

    public static HasRequestFromFactory hasRequestFromFactory(Class<?> toMatch) {
     return new HasRequestFromFactory(toMatch);
    }

    private static class HasRequestFromFactory extends ArgumentMatcher<RequestFactory> {
        private final Class<?> toMatch;

        public HasRequestFromFactory(Class<?> toMatch) {
            this.toMatch = toMatch;
        }

        @Override
        public boolean matches(Object argument) {
            return toMatch.isAssignableFrom(((RequestFactory) argument).call().getClass());
        }
    }
}
