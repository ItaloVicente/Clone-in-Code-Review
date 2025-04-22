
	@Override
	public @NonNull List<RefSpec> getRefSpecs(Config config
			String subsection
		String[] values = config.getStringList(section
		List<RefSpec> result = new ArrayList<>(values.length);
		for (String spec : values) {
			result.add(new RefSpec(spec));
		}
		return result;
	}
