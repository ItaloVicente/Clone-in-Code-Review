	private boolean isDefaultFastView(MPlaceholder placeholder) {
		if (defaultFastViews == null) {
			defaultFastViews = perspReader.getDefaultFastViewBarViewIds();
		}
		return defaultFastViews.contains(placeholder.getElementId());
	}

	private boolean isDefaultFastView(String placeholderId) {
		if (defaultFastViews == null) {
			defaultFastViews = perspReader.getDefaultFastViewBarViewIds();
		}
		return defaultFastViews.contains(placeholderId);
	}

