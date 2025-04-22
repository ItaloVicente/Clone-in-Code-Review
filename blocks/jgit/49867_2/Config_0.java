	public String get(String section
		String[] self = getRawStringList(section
		if (self == null && baseConfig == null) {
			return null;
		}
		if (self == null) {
			return baseConfig.get(section
		}
		if (self[0] == null) {
			return "";
		}
		return self[0];
	}

