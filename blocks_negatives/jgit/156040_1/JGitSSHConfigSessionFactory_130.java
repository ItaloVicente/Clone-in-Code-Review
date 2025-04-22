    @Override
    protected void configure(final OpenSshConfig.Host hc,
                             final Session session) {
        final CredentialsProvider provider = new CredentialsProvider() {
            @Override
            public boolean isInteractive() {
                return false;
            }
