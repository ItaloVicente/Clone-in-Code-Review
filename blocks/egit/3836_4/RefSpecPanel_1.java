	private String deletePrefixes(String ref, String... prefixes) {
		for (String prefix : prefixes)
			if (ref.startsWith(prefix))
				return ref.substring(prefix.length());

		return ref;
	}

