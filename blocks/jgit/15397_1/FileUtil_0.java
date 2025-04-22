	public static String normalize(String name) {
		if (SystemReader.getInstance().isMacOS()) {
			if (name == null)
				return null;
			String normalized = Normalizer.normalize(name
			return normalized;
		}
		return name;
	}

