		try {
			editableConfig.clear();
			editableConfig.load();
		} catch (ConfigInvalidException e) {
			throw new IOException(e.getMessage());
		}
		initControlsFromConfig();
