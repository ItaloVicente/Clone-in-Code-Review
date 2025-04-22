	private void putGlobalIndex() throws DhtException {
		for (linkedIdx = 0; linkedIdx < objectListByName.size(); linkedIdx++) {
			DhtInfo oe = objectListByName.get(linkedIdx);
			ObjectIndexKey objKey = ObjectIndexKey.create(repo, oe);
			ChunkKey chunkKey = chunkOf(oe.chunkPtr);
			db.objectIndex().add(objKey, oe.info(chunkKey), dbWriteBuffer);
