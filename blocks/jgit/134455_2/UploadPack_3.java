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
			Map<String
			for (String p : refPrefixes) {
				for (Ref r : db.getRefDatabase().getRefsByPrefix(p)) {
					rs.put(r.getName()
				}
			}
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

