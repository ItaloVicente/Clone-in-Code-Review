	private void push(List<String> list) {
		List<ChunkKey> keys = new ArrayList<ChunkKey>(list.size());
		for (String keyString : list)
			keys.add(ChunkKey.fromString(keyString));
		push(keys);
	}

