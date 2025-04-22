	public EGitSshdSessionFactory(IProxyService service) {
		super(null, new EGitProxyDataFactory(service));
		SshPreferencesMirror.INSTANCE.start();
	}

	@Override
	public void close() {
		SshPreferencesMirror.INSTANCE.stop();
		super.close();
