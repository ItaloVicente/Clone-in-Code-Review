		ChunkKey key = ChunkKey.create(repo

		GitStore.ChunkInfo.Builder info = GitStore.ChunkInfo.newBuilder();
		info.setSource(source);
		info.setObjectType(GitStore.ChunkInfo.ObjectType.valueOf(objectType));
		if (fragment)
			info.setIsFragment(true);
		info.setChunkSize(chunkData.length);

		GitStore.ChunkInfo.ObjectCounts.Builder cnts = info.getObjectCountsBuilder();
		cnts.setTotal(objectsTotal);
		if (objectsWhole > 0)
			cnts.setWhole(objectsWhole);
		if (objectsRefDelta > 0)
			cnts.setRefDelta(objectsRefDelta);
		if (objectsOfsDelta > 0)
			cnts.setOfsDelta(objectsOfsDelta);
