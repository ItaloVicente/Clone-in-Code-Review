		List<DfsPackFile> packs = sortPacksForSelectRepresentation();
		trySelectRepresentation(packer

		List<DfsPackFile> garbage = garbagePacksForSelectRepresentation();
		if (!garbage.isEmpty() && checkGarbagePacks(objects)) {
			trySelectRepresentation(packer
		}
	}

	private void trySelectRepresentation(PackWriter packer
			ProgressMonitor monitor
			List<DfsPackFile> packs
		for (DfsPackFile pack : packs) {
			List<DfsObjectToPack> tmp = findAllFromPack(pack
