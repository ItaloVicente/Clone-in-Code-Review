
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		CredentialsProvider.setDefault(new EGitCredentialsProvider());
	}

