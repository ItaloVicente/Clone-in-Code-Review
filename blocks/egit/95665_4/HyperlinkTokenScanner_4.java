		if (configuredDetectors != null && configuredDetectors.length > 0) {
			for (IHyperlinkDetector detector : configuredDetectors) {
				allDetectors.add(detector);
			}
			if (preferenceStore.getBoolean(URL_HYPERLINK_DETECTOR_KEY)
					|| !preferenceStore.getBoolean(
							AbstractTextEditor.PREFERENCE_HYPERLINKS_ENABLED)) {
				return allDetectors;
			}
