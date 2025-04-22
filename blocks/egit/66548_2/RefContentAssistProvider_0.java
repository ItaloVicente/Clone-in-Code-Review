		this.container = null;
	}

	public RefContentAssistProvider(Repository repo, URIish uri,
			IWizardContainer container) {
		this.repo = repo;
		this.uri = uri;
		this.shell = null;
		this.container = container;
