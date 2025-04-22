	public static void registerFormat(String name
		if (formats.putIfAbsent(name
			throw new JGitInternalException(
					"Archive format already registered: " + name);
	}

	public static void unregisterFormat(String name) {
		if (formats.remove(name) == null)
			throw new JGitInternalException(
					"Archive format already absent: " + name);
