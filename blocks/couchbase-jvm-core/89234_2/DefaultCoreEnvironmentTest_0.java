    @Test
    public void shouldSupportChildBuilder() {
        DummyEnvironment env = new ChildBuilder()
            .continuousKeepAliveEnabled(true)
            .used()
            .socketConnectTimeout(1234)
            .build();
        assertTrue(env != null);
    }

    static class ChildBuilder extends DefaultCoreEnvironment.Builder<ChildBuilder> {

        private boolean used = false;

        public ChildBuilder used() {
            used = true;
            return this;
        }

        public DummyEnvironment build() {
            return new DummyEnvironment(this);
        }
    }

    static class DummyEnvironment extends DefaultCoreEnvironment {
        public DummyEnvironment(Builder builder) {
            super(builder);
        }
    }

