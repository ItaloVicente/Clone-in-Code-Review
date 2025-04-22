	public void save() throws IOException {
		editableConfig.save();
		setDirty(false);
		initControlsFromConfig();
	}

	public void restore() throws IOException {
		try {
			editableConfig.load();
			tv.refresh();
		} catch (ConfigInvalidException e) {
			throw new IOException(e.getMessage());
		}
		initControlsFromConfig();
	}

