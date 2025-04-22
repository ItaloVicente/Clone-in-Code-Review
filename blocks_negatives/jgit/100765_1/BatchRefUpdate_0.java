		for (String name : names)
			ref.addAll(getPrefixes(name));
		return ref;
	}

	private static void addRefToPrefixes(Collection<String> prefixes,
			String name) {
		for (String prefix : getPrefixes(name)) {
			prefixes.add(prefix);
