	private PackWriter newPackWriter() {
		PackConfig pc = packConfig;
		if (pc == null) {
			pc = db != null ? new PackConfig(db) : new PackConfig();
		}
		return new PackWriter(pc
	}

