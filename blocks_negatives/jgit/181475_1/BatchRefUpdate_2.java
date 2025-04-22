	private static Collection<String> getTakenPrefixes(Collection<String> names) {
		Collection<String> ref = new HashSet<>();
		for (String name : names) {
			addPrefixesTo(name, ref);
		}
		return ref;
	}

