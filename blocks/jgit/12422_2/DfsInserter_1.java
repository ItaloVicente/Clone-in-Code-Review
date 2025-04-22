	@Override
	public ObjectReader newReader() throws IOException {
		if (packOut == null)
			beginPack();
		return new Reader();
	}

