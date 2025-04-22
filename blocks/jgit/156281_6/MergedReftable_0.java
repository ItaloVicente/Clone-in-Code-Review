	@Override
	public long minUpdateIndex() throws IOException {
		return tables.length > 0 ? tables[0].minUpdateIndex()
			: 0;
	}

