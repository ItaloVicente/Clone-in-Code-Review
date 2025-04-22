			for (ChunkKey key : list) {
				ChunkInfo oldInfo = infoByKey.get(key);
				GitStore.ChunkInfo.Builder b =
					GitStore.ChunkInfo.newBuilder(oldInfo.getData());
				b.setCachedPackKey(cachedPackKey.asString());
				ChunkInfo newInfo = new ChunkInfo(key
				infoByKey.put(key

				if (newInfo.getData().getIsFragment())
					db.repository().put(repo
