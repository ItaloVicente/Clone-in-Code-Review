	private @NonNull IHyperlinkDetector[] getHyperlinkDetectors() {
		IHyperlinkDetector[] allDetectors;
		IHyperlinkDetector[] configuredDetectors = configuration
				.getHyperlinkDetectors(viewer);
		if (configuredDetectors == null || configuredDetectors.length == 0) {
			allDetectors = new IHyperlinkDetector[0];
		} else {
			allDetectors = new IHyperlinkDetector[configuredDetectors.length
					+ 1];
			System.arraycopy(configuredDetectors, 0, allDetectors, 0,
					configuredDetectors.length);
			allDetectors[configuredDetectors.length] = new MultiURLHyperlinkDetector(
					preferenceStore);
		}
		return allDetectors;
	}

