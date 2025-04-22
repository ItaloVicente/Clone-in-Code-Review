	public <T extends ObjectId> Set<ObjectId> hasObject(Iterable<T> toFind)
			throws IOException {
		PackFile[] packs = new PackFile[packNames.length];
		for (int i = 0; i < packNames.length; i++)
			packs[i] = getPackFile(packNames[i]);

		Set<ObjectId> have = new HashSet<ObjectId>();
		for (ObjectId id : toFind) {
			for (PackFile pack : packs) {
				if (pack.hasObject(id)) {
					have.add(id);
					break;
				}
