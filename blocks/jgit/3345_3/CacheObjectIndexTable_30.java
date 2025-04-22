		private Collection<ObjectInfo> decode(byte[] value)
				throws InvalidProtocolBufferException {
			CachedObjectIndex cacheEntry = CachedObjectIndex.parseFrom(value);
			int sz = cacheEntry.getItemCount();
			ObjectInfo[] r = new ObjectInfo[sz];
			for (int i = 0; i < sz; i++) {
				CachedObjectIndex.Item item = cacheEntry.getItem(i);
				r[i] = new ObjectInfo(
						ChunkKey.fromString(item.getChunkKey())
						item.getTime()
						item.getObjectInfo());
			}
			return Arrays.asList(r);
		}

