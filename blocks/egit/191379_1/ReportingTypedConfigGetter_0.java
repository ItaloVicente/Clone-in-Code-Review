	@Override
	public int getIntInRange(Config config, String section, String subsection,
			String name, int minValue, int maxValue, int defaultValue) {
		try {
			return super.getIntInRange(config, section, subsection, name,
					minValue, maxValue, defaultValue);
		} catch (IllegalArgumentException e) {
			warn(config, join(section, subsection, name),
					Integer.toString(defaultValue), e);
			return defaultValue;
		}
	}

