	private IndexPack newIndexPack() throws IOException {
		final IndexPack ip = IndexPack.create(transport.local, bin);
		ip.setFixThin(true);
		ip.setObjectChecking(transport.isCheckFetchedObjects());
		return ip;
	}

