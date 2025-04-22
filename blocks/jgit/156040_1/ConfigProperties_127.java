	private final Map<String
	private final List<ConfigProperty> configSummary = new ArrayList<>();

	public ConfigProperties(Map<String
		this.configuredValues = checkNotNull("configuredValues"
	}

	public ConfigProperties(Properties configuredValues) {
		Map<String
		for (String key : configuredValues.stringPropertyNames()) {
			stringProperties.put(key
		}
		this.configuredValues = stringProperties;
	}

	public ConfigProperty get(String name
		String val = configuredValues.get(name);
		ConfigProperty cp;
		if (val == null || val.trim().length() == 0) {
			cp = new ConfigProperty(name
		} else {
			cp = new ConfigProperty(name
		}
		configSummary.add(cp);
		return cp;
	}

	public String getConfigurationSummary(String heading) {
		final String newLine = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder(heading);
		for (ConfigProperty cp : configSummary) {
			sb.append(newLine).append(cp);
		}
		return sb.toString();
	}

	public static class ConfigProperty {

		private final String name;
		private final String value;
		private final boolean isDefault;

		ConfigProperty(String name
			this.name = name;
			this.value = value;
			this.isDefault = isDefault;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public boolean isDefault() {
			return isDefault;
		}

		public boolean getBooleanValue() {
			return Boolean.parseBoolean(value);
		}

		public int getIntValue() {
			return Integer.parseInt(value);
		}

		@Override
		public String toString() {
			return name + " = \"" + value + "\"" + (isDefault ? " (Defaulted)" : "");
		}
	}
