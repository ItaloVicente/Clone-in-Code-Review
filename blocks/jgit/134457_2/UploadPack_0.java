	@Nullable
	private Ref getRef(String name) throws IOException {
		if (refs != null) {
			return refs.get(name);
		}
		if (!advertiseRefsHookCalled) {
			advertiseRefsHook.advertiseRefs(this);
			advertiseRefsHookCalled = true;
		}
		if (refs == null &&
				refFilter == RefFilter.DEFAULT &&
				transferConfig.hasDefaultRefFilter()) {
			return db.getRefDatabase().exactRef(name);
		}
		return getAdvertisedOrDefaultRefs().get(name);
	}

