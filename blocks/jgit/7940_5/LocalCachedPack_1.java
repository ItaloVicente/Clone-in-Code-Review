	LocalCachedPack(List<PackFile> packs) {
		odb = null;
		tips = null;
		packNames = null;
		this.packs = packs.toArray(new PackFile[packs.size()]);
	}

