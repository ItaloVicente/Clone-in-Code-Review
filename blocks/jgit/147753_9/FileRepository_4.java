		StoredConfig userConfig = null;
		try {
			userConfig = SystemReader.getInstance().getUserConfig();
		} catch (ConfigInvalidException e) {
			LOG.error(e.getMessage()
			throw new IOException(e.getMessage()
		}
