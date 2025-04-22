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
