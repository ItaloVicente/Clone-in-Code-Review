	private long getUnoffloadedObjectCount() throws IOException {
		long objCnt = 0;

		objCnt += objectsLists[OBJ_COMMIT].size();
		objCnt += objectsLists[OBJ_TREE].size();
		objCnt += objectsLists[OBJ_BLOB].size();
		objCnt += objectsLists[OBJ_TAG].size();

		for (CachedPack pack : cachedPacks) {
			CachedPackUriProvider.PackInfo packInfo =
				packfileUriConfig.cachedPackUriProvider.getInfo(
					pack
			if (packInfo == null) {
				objCnt += pack.getObjectCount();
			}
		}

		return objCnt;
	}

