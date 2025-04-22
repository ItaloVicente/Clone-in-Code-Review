		return Collections.unmodifiableSet(stringToRGB.keySet());
	}

	public RGB getRGB(String symbolicName) {
		Assert.isNotNull(symbolicName);
		return stringToRGB.get(symbolicName);
	}

