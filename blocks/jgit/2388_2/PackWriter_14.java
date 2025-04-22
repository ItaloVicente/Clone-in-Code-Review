		for (CachedPack p : cachedPacks) {
			for (ObjectId d : p.hasObject(objectsLists[Constants.OBJ_COMMIT])) {
				if (baseTrees.size() <= maxBases)
					baseTrees.add(walker.lookupCommit(d).getTree());
				objectsMap.get(d).setEdge();
				typesToPrune |= 1 << Constants.OBJ_COMMIT;
			}
		}

