	public RemoteListCommand remoteList() {
		return new RemoteListCommand(repo);
	}

	public RemoteAddCommand remoteAdd() {
		return new RemoteAddCommand(repo);
	}

	public RemoteRemoveCommand remoteRemove() {
		return new RemoteRemoveCommand(repo);
	}

	public RemoteSetUrlCommand remoteSetUrl() {
		return new RemoteSetUrlCommand(repo);
	}

