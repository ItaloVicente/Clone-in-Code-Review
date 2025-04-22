		this.server = server;
	}

	@Override
	protected String getSettingsKey() {
		return '.' + server.getType().getId();
