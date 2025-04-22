
		for (CachedPack p : cachedPacks) {
			for (ObjectId d : p.hasObject(objectsLists[Constants.OBJ_TREE])) {
				objectsMap.get(d).setEdge();
				typesToPrune |= 1 << Constants.OBJ_TREE;
			}
			for (ObjectId d : p.hasObject(objectsLists[Constants.OBJ_BLOB])) {
				objectsMap.get(d).setEdge();
				typesToPrune |= 1 << Constants.OBJ_BLOB;
			}
			for (ObjectId d : p.hasObject(objectsLists[Constants.OBJ_TAG])) {
				objectsMap.get(d).setEdge();
				typesToPrune |= 1 << Constants.OBJ_TAG;
			}
		}

		if (typesToPrune != 0) {
			pruneObjectList(typesToPrune
			pruneObjectList(typesToPrune
			pruneObjectList(typesToPrune
			pruneObjectList(typesToPrune
		}

		for (CachedPack pack : cachedPacks)
			countingMonitor.update((int) pack.getObjectCount());
