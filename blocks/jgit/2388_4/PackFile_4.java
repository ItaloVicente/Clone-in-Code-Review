	public String getPackName() {
		String name = getPackFile().getName();
		if (name.startsWith("pack-"))
			name = name.substring("pack-".length());
		if (name.endsWith(".pack"))
			name = name.substring(0
		return name;
	}

