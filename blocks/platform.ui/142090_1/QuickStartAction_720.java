	public boolean openWelcomePage(String featureId) throws WorkbenchException {
		AboutInfo feature = findFeature(featureId);
		if (feature == null || feature.getWelcomePageURL() == null) {
			return false;
		}
		return openWelcomePage(feature);
	}
