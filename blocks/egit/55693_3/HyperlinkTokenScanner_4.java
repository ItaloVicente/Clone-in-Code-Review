	private @NonNull IHyperlinkDetector[] getHyperlinkDetectors() {
		IHyperlinkDetector[] allDetectors;
		IHyperlinkDetector[] configuredDetectors = configuration
				.getHyperlinkDetectors(viewer);
		if (configuredDetectors == null || configuredDetectors.length == 0) {
			allDetectors = new IHyperlinkDetector[0];
		} else if (preferenceStore.getBoolean(URL_HYPERLINK_DETECTOR_KEY)
				|| !preferenceStore.getBoolean(
						AbstractTextEditor.PREFERENCE_HYPERLINKS_ENABLED)) {
			allDetectors = configuredDetectors;
		} else {
			allDetectors = new IHyperlinkDetector[configuredDetectors.length
					+ 1];
			System.arraycopy(configuredDetectors, 0, allDetectors, 0,
					configuredDetectors.length);
			allDetectors[configuredDetectors.length] = new MultiURLHyperlinkDetector();
		}
		return allDetectors;
	}

