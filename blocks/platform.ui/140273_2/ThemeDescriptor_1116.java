		if (name == null)
			return getId();
		return name;
	}

	void extractName(IConfigurationElement configElement) {
		if (name == null) {
