		if (fileRegions == null || fileRegions.length == 0) {
			FileDiff implicitFileDiff = defaultFileDiff;
			if (implicitFileDiff != null) {
				fileRegions = new FileDiffRegion[] {
						new FileDiffRegion(implicitFileDiff, 0, getLength()) };
			}
