	@Override
	public @NonNull List<RefSpec> getRefSpecs(Config config, String section,
			String subsection, String name) {
		String[] values = config.getStringList(section, subsection, name);
		List<RefSpec> result = new ArrayList<>(values.length);
		for (String spec : values) {
			try {
				result.add(new RefSpec(spec));
			} catch (IllegalArgumentException e) {
				warn(config, join(section, subsection, name), null, e);
			}
		}
		return result;
	}

