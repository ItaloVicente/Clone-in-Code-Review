	private void useCachedPack(ObjectWalk walker, RevFlagSet keepOnRestart,
			List<RevObject> wantObj, List<RevObject> baseObj, CachedPack pack)
			throws MissingObjectException, IncorrectObjectTypeException,
			IOException {
		cachedPacks.add(pack);
		for (ObjectId id : pack.getTips())
			baseObj.add(walker.lookupOrNull(id));

		setThin(true);
		walker.resetRetain(keepOnRestart);
		walker.sort(RevSort.TOPO);
		walker.sort(RevSort.BOUNDARY, true);

		for (RevObject id : wantObj)
			walker.markStart(id);
		for (RevObject id : baseObj)
			walker.markUninteresting(id);
	}

	private static boolean includesAllTips(CachedPack pack, RevFlag include,
			ObjectWalk walker) {
		for (ObjectId id : pack.getTips()) {
			if (!walker.lookupOrNull(id).has(include))
				return false;
		}
		return true;
	}

