	public long getObjectsNumber() throws IOException {
		if (stats.totalObjects == 0) {
			long objCnt = 0;
			for (List<ObjectToPack> list : objectsLists)
				objCnt += list.size();
			for (CachedPack pack : cachedPacks)
				objCnt += pack.getObjectCount();
			return objCnt;
		}
