	public Collection<DfsPackDescription> getSourcePacks() {
		Set<DfsPackDescription> src = new HashSet<>();
		for (DfsPackFile pack : srcPacks) {
			src.add(pack.getPackDescription());
		}
		for (DfsReftable table : srcReftables) {
			src.add(table.getPackDescription());
		}
		return src;
