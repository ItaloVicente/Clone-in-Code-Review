		monitor.endTask();
	}

	private static Collection<String> getTakenPrefixes(
			final Collection<String> names) {
		Collection<String> ref = new HashSet<String>();
		for (String name : names)
			ref.addAll(getPrefixes(name));
		return ref;
	}

	private static void addRefToPrefixes(Collection<String> prefixes
			String name) {
		for (String prefix : getPrefixes(name)) {
			prefixes.add(prefix);
		}
	}

	static Collection<String> getPrefixes(String s) {
		Collection<String> ret = new HashSet<String>();
		int p1 = s.indexOf('/');
		while (p1 > 0) {
			ret.add(s.substring(0
			p1 = s.indexOf('/'
		}
		return ret;
