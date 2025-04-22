	private static DfsPackDescription oldestPack(DfsPackDescription a,
			DfsPackDescription b) {
		if (a != null && a.getLastModified() < b.getLastModified()) {
			return a;
		}
		return b;
	}

