        verify(core, times(1)).send(argThat(new IsRequestInFactory(RemoveRequest.class)));
    }

    class IsRequestInFactory extends ArgumentMatcher<RequestFactory> {
        private final Class<?> toMatch;

        public IsRequestInFactory(Class<?> toMatch) {
            this.toMatch = toMatch;
        }

        @Override
        public boolean matches(Object argument) {
            return toMatch.isAssignableFrom(((RequestFactory) argument).call().getClass());
        }
