	private List<DfsPackDescription> toPrune() {
		int cnt = srcPacks.size();
		List<DfsPackDescription> all = new ArrayList<>(cnt);
		for (DfsPackFile pack : srcPacks)
			all.add(pack.getPackDescription());
		return all;
