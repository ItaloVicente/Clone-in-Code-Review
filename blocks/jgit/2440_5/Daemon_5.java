	public void setRepositoryResolver(RepositoryResolver<DaemonClient> resolver) {
		repositoryResolver = resolver;
	}

	@SuppressWarnings("unchecked")
	public void setUploadPackFactory(UploadPackFactory<DaemonClient> factory) {
		if (factory != null)
			uploadPackFactory = factory;
		else
			uploadPackFactory = (UploadPackFactory<DaemonClient>) UploadPackFactory.DISABLED;
	}

	@SuppressWarnings("unchecked")
	public void setReceivePackFactory(ReceivePackFactory<DaemonClient> factory) {
		if (factory != null)
			receivePackFactory = factory;
		else
			receivePackFactory = (ReceivePackFactory<DaemonClient>) ReceivePackFactory.DISABLED;
	}

