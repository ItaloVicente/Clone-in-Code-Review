
	public void setFetchOnFeatureStart(boolean isFetchOnFeatureStart)
			throws IOException {
		StoredConfig config = repository.getConfig();
		config.setBoolean(GITFLOW_SECTION, FEATURE_START_SUBSECTION, FEATURE_START_FETCH_KEY,
				isFetchOnFeatureStart);
		config.save();
	}

	public boolean isFetchOnFeatureStart() {
		StoredConfig config = repository.getConfig();
		return config.getBoolean(GITFLOW_SECTION, FEATURE_START_SUBSECTION,
				FEATURE_START_FETCH_KEY, false);
	}
