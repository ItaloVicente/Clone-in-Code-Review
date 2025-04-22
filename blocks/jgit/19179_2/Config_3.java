	public Set<String> getNames(String section
		return getNames(section
	}

	public Set<String> getNames(String section
			boolean recursive) {
		if (!recursive)
			return getNames(section

		Set<String> names = new HashSet<String>(getNames(section
		Config c = this;
		while ((c = c.baseConfig) != null)
			names.addAll(c.getNames(section

		return names;
	}

