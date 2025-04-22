	private PackWriter newPackWriter() {
		PackConfig pc = packConfig;
		if (pc == null) {
			pc = db != null ? new PackConfig(db) : new PackConfig();
		}
		if (reader != null) {
			return new PackWriter(pc
		}
		final ObjectReader or = db.newObjectReader();
		return new PackWriter(pc
			@Override
			public void close() {
				super.close();
				or.close();
			}
		};
	}

