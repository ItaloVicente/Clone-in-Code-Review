
	public String getPrefixKey(String branchName) {
		String featurePrefix = getFeaturePrefix();
		String releasePrefix = getReleasePrefix();
		String hotfixPrefix = getHotfixPrefix();
		if (branchName.startsWith(featurePrefix)) {
			return FEATURE_KEY;
		} else if (branchName.startsWith(releasePrefix)) {
			return RELEASE_KEY;
		} else if (branchName.startsWith(hotfixPrefix)) {
			return HOTFIX_KEY;
		}
		return null;
	}
