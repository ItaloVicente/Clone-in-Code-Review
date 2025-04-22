		List<DfsPackFile> packs = sortPacksForSelectRepresentation();
		trySelectObjectRepresentation(packer

		List<DfsPackFile> garbage = garbagePacksForSelectRepresentation();
		if (!garbage.isEmpty() && checkGarbagePacks(objects)) {
			trySelectObjectRepresentation(packer
		}
	}

	private void trySelectObjectRepresentation(PackWriter packer
			ProgressMonitor monitor
			List<DfsPackFile> packs) throws IOException {
		for (DfsPackFile pack : packs) {
