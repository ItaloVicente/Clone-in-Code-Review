						.getLocalName())
				base.fetchSpecs
	}

	RemoteRefUpdate(Repository localDb
			@NonNull Collection<RefSpec> fetchSpecs) {
		this.localDb = localDb;
		this.forceUpdate = forceUpdate;
		this.fetchSpecs = fetchSpecs;
		this.trackingRefUpdate = null;
		this.srcRef = null;
		this.remoteName = null;
		this.newObjectId = null;
		this.status = Status.NOT_ATTEMPTED;
	}

	public boolean isMatching() {
		return fetchSpecs != null;
	}

	Collection<RefSpec> getFetchSpecs() {
		return fetchSpecs;
