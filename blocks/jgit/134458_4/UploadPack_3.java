	private Map<String
					throws IOException {
		if (refPrefixes.isEmpty()) {
			return getAdvertisedOrDefaultRefs();
		}
		if (refs == null && !advertiseRefsHookCalled) {
			advertiseRefsHook.advertiseRefs(this);
			advertiseRefsHookCalled = true;
		}
		if (refs == null) {
			String[] prefixes = refPrefixes.toArray(new String[0]);
			Map<String
					db.getRefDatabase().getRefsByPrefix(prefixes).stream()
						.collect(toMap(Ref::getName
			if (refFilter != RefFilter.DEFAULT) {
				return refFilter.filter(rs);
			}
			return transferConfig.getRefFilter().filter(rs);
		}

		return refs.values().stream()
				.filter(ref -> refPrefixes.stream()
						.anyMatch(ref.getName()::startsWith))
				.collect(toMap(Ref::getName
	}

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

