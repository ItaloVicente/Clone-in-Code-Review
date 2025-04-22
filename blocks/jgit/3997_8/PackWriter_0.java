	public void excludeObjects(PackIndex idx) {
		if (excludeInPacks == null) {
			excludeInPacks = new PackIndex[] { idx };
			excludeInPackLast = idx;
		} else {
			int cnt = excludeInPacks.length;
			PackIndex[] newList = new PackIndex[cnt + 1];
			System.arraycopy(excludeInPacks
			newList[cnt] = idx;
			excludeInPacks = newList;
		}
	}

