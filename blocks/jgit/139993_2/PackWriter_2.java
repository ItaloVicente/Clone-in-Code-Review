	private long getUnoffloadedObjectCount(PackfileUriConfig packfileUriConfig)
			throws IOException {

		long objCnt = 0;

		objCnt += objectsLists[OBJ_COMMIT].size();
		objCnt += objectsLists[OBJ_TREE].size();
		objCnt += objectsLists[OBJ_BLOB].size();
		objCnt += objectsLists[OBJ_TAG].size();

		for (CachedPack pack : cachedPacks) {
			if (!packfileUriConfig.cachedPackUriProvider.hasUri(
						pack
				objCnt += pack.getObjectCount();
			}
		}

		return objCnt;
	}

