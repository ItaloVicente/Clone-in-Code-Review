
	protected boolean removeCachedIconUri(Map<String, Object> transientData) {
		boolean containsURI = false;
		if (transientData.containsKey(ICON_URI_FOR_PART)) {
			transientData.remove(ICON_URI_FOR_PART);
			containsURI = true;
		}
		return containsURI;
	}
