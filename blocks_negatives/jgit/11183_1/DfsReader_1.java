	public Collection<CachedPack> getCachedPacks() throws IOException {
		DfsPackFile[] packList = db.getPacks();
		List<CachedPack> cached = new ArrayList<CachedPack>(packList.length);
		for (DfsPackFile pack : packList) {
			DfsPackDescription desc = pack.getPackDescription();
			if (canBeCachedPack(desc))
				cached.add(new DfsCachedPack(pack));
		}
		return cached;
	}

	private static boolean canBeCachedPack(DfsPackDescription desc) {
		return desc.getTips() != null && !desc.getTips().isEmpty();
	}

