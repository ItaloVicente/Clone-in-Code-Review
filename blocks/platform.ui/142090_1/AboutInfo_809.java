		URL url = bundleGroupProperties.getFeatureImageUrl();
		return url == null ? null : new Path(url.getPath()).lastSegment();
	}

	public Long getFeatureImageCRC() {
		if (bundleGroupProperties == null) {
