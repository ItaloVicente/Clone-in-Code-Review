
	public void setFetchOnFeatureStart(boolean isFetchOnFeatureStart)
			throws IOException {
		StoredConfig config = repository.getConfig();
		config.setBoolean(GITFLOW_SECTION, FEATURE_START_SUBSECTION, FEATURE_START_FETCH_KEY,
				isFetchOnFeatureStart);
		config.save();
	}

	public boolean isFetchOnFeatureStart() {
		StoredConfig config = repository.getConfig();
		String result = config.getString(GITFLOW_SECTION,
				FEATURE_START_SUBSECTION, FEATURE_START_FETCH_KEY);
		return (result == null) ? false
				: Boolean.valueOf(result).booleanValue();
	}
