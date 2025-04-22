	public static String normalize(String name) {
		if (SystemReader.getInstance().isMacOS()) {
			if (name == null)
				return null;
			return Normalizer.normalize(name
		}
		return name;
	}

