		IHyperlinkDetector[] allDetectors;
		if (hyperlinkDetectors == null || hyperlinkDetectors.length == 0) {
			allDetectors = new IHyperlinkDetector[0];
		} else {
			allDetectors = new IHyperlinkDetector[hyperlinkDetectors.length
					+ 1];
			System.arraycopy(hyperlinkDetectors, 0, allDetectors, 0,
					hyperlinkDetectors.length);
			allDetectors[hyperlinkDetectors.length] = new MultiURLHyperlinkDetector();
		}
		this.hyperlinkDetectors = allDetectors;
