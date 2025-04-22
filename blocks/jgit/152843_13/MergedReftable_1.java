	@Override
	public boolean hasObjectMap() throws IOException {
		boolean has = true;
		for (int i = 0; has && i < tables.length; i++) {
			has = has && tables[i].hasObjectMap();
		};
		return has;
	}

