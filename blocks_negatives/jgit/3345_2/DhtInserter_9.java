		activeChunk.end(digest());
		activeChunk.safePut(db, dbBuffer());
		activeChunk = newChunk();

		if (activeChunk.whole(deflater(), type, data, off, len, objId))
			return objId;
