	private String deletePrefixes(String ref, String... prefixes) {
		String result = ref;
		for (String prefix : prefixes) {
			if (result.startsWith(prefix)) {
				result = result.substring(prefix.length());
				return result;
			}
		}
		return result;
	}

