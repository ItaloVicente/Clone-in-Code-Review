	protected DfsPackDescription newPack(PackSource source
			long estimatedPackSize) throws IOException {
		DfsPackDescription pack = newPack(source);
		pack.setEstimatedPackSize(estimatedPackSize);
		return pack;
	}

