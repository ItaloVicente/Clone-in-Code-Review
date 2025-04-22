    class IsRequestInFactory extends ArgumentMatcher<RequestFactory> {
        @Override
        public boolean matches(Object argument) {
            return false;
        }
    }

