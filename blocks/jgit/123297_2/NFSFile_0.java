	public static NFSFile resolve(final File dir
			final Config config) {
		final NFSFile abspn = new NFSFile(name
		if (abspn.isAbsolute())
			return abspn;
		return new NFSFile(dir
	}

