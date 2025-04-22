		if (keySet == null)
			init();
		return keySet.contains(objrep.getChunkKey());
	}

	private void init() {
		ChunkList chunkList = info.getChunkList();
		int cnt = chunkList.getChunkKeyCount();
		keySet = new HashSet<ChunkKey>();
		keyList = new ChunkKey[cnt];
		for (int i = 0; i < cnt; i++) {
			ChunkKey key = ChunkKey.fromString(chunkList.getChunkKey(i));
			keySet.add(key);
			keyList[i] = key;
		}
