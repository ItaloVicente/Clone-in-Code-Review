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

	@NonNull
	private static Map<String
			Map<String
		return unmodifiableMap(
				names.stream()
					.map(refs::get)
					.filter(Objects::nonNull)
					.collect(toMap(Ref::getName
	}

	@NonNull
	private Map<String
		if (refs != null) {
			return mapRefs(refs
		}
		if (!advertiseRefsHookCalled) {
			advertiseRefsHook.advertiseRefs(this);
			advertiseRefsHookCalled = true;
		}
		if (refs == null &&
				refFilter == RefFilter.DEFAULT &&
				transferConfig.hasDefaultRefFilter()) {
			String[] ns = names.toArray(new String[0]);
			return unmodifiableMap(db.getRefDatabase().exactRef(ns));
		}
		return mapRefs(getAdvertisedOrDefaultRefs()
	}

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
			return db.getRefDatabase().findRef(name);
		}
		return RefDatabase.findRef(getAdvertisedOrDefaultRefs()
	}

