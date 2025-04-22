		initSshSessionFactory();
	}

	TransportGitSsh(final URIish uri) {
		super(uri);
		initSshSessionFactory();
	}

    private void initSshSessionFactory() {
