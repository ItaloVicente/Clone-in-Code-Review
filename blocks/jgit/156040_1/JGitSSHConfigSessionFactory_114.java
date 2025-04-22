	@Override
	protected void configure(final OpenSshConfig.Host hc
		final CredentialsProvider provider = new CredentialsProvider() {
			@Override
			public boolean isInteractive() {
				return false;
			}
