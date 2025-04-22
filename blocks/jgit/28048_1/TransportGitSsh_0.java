		initSshFactory();
	}

    TransportGitSsh(final URIish uri) {
        super(uri);
        initSshFactory();
    }

    private void initSshFactory() {
