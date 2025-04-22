		ChunkAndOffset r = ChunkCache.get().find(repo, objId);
		if (r != null)
			return r;

		if (!loadIfRequired)
			return null;

