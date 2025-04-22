	@Nullable
	private Ref findRef(String name) throws IOException {
		if (refs != null) {
			return RefDatabase.findRef(refs
		}
		if (!advertiseRefsHookCalled) {
			advertiseRefsHook.advertiseRefs(this);
			advertiseRefsHookCalled = true;
		}
		if (refs == null &&
				refFilter == RefFilter.DEFAULT &&
				transferConfig.hasDefaultRefFilter()) {
			return db.getRefDatabase().getRef(name);
		}
		return RefDatabase.findRef(getAdvertisedOrDefaultRefs()
	}

