	public PackConfig getPackConfig() {
		if (packConfig == null)
			packConfig = new PackConfig(local);
		return packConfig;
	}

	public void setPackConfig(PackConfig pc) {
		packConfig = pc;
	}

