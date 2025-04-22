	public Collection<String> getConflictingNames(String name)
			throws IOException {
		Map<String
		int lastSlash = name.lastIndexOf('/');
		while (0 < lastSlash) {
			String needle = name.substring(0
			if (allRefs.containsKey(needle))
				return Collections.singletonList(needle);
			lastSlash = name.lastIndexOf('/'
		}

		List<String> conflicting = new ArrayList<String>();
		String prefix = name + '/';
		for (String existing : allRefs.keySet())
			if (existing.startsWith(prefix))
				conflicting.add(existing);

		return conflicting;
	}

