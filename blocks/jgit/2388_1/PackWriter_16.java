	private void pruneObjectList(int typesToPrune
		if ((typesToPrune & (1 << typeCode)) == 0)
			return;

		final List<ObjectToPack> list = objectsLists[typeCode];
		final int size = list.size();
		int src = 0;
		int dst = 0;

		for (; src < size; src++) {
			ObjectToPack obj = list.get(src);
			if (obj.isEdge())
				continue;
			if (dst != src)
				list.set(dst
			dst++;
		}

		while (dst < list.size())
			list.remove(dst);
	}

	private void useCachedPack(ObjectWalk walker
			List<RevObject> wantObj
			throws MissingObjectException
			IOException {
		cachedPacks.add(pack);
		for (ObjectId id : pack.getTips())
			baseObj.add(walker.lookupOrNull(id));

		objectsMap.clear();
		objectsLists[Constants.OBJ_COMMIT] = new ArrayList<ObjectToPack>();

		setThin(true);
		walker.resetRetain(keepOnRestart);
		walker.sort(RevSort.TOPO);
		walker.sort(RevSort.BOUNDARY

		for (RevObject id : wantObj)
			walker.markStart(id);
		for (RevObject id : baseObj)
			walker.markUninteresting(id);
	}

	private static boolean includesAllTips(CachedPack pack
			ObjectWalk walker) {
		for (ObjectId id : pack.getTips()) {
			if (!walker.lookupOrNull(id).has(include))
				return false;
		}
		return true;
	}

