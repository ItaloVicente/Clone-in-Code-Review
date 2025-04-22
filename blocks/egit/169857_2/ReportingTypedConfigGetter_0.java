	@Override
	public Path getPath(Config config, String section, String subsection,
			String name, @NonNull FS fs, File resolveAgainst,
			Path defaultValue) {
		try {
			return super.getPath(config, section, subsection, name, fs,
					resolveAgainst, defaultValue);
		} catch (IllegalArgumentException e) {
			warn(config, join(section, subsection, name),
					defaultValue == null ? null : defaultValue.toString(), e);
			return defaultValue;
		}
	}

