	private Map<String, Ref> getAdvertisedOrDefaultRefs() {
		if (refs == null)
			setAdvertisedRefs(null, null);
		return refs;
	}

