	@Override
	public String getProperty(String name, String defaultValue) {
		Map<String, String> properties = getProperties();
		if (properties == null || properties.isEmpty()) {
			return defaultValue;
		}
		return super.getProperty(name, defaultValue);
	}

