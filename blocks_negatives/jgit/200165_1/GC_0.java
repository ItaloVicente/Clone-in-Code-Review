		pm.start(6 /* tasks */);
		packRefs();
		Collection<Pack> newPacks = repack();
		prune(Collections.emptySet());
		if (shouldWriteCommitGraphWhenGc()) {
			writeCommitGraph(refsToObjectIds(getAllRefs()));
		}
		return newPacks;
	}
