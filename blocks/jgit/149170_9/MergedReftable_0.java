	@Override
	public long maxUpdateIndex() throws IOException {
		return tables.length > 0 ? tables[tables.length - 1].maxUpdateIndex()
				: 0;
	}

