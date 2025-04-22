			if (loadIfRequired) {
				chunk = load(key);
				if (chunk != null && chunk.hasIndex()) {
					int pos = chunk.findOffset(repo, objId);
					if (0 <= pos) {
						chunk = ChunkCache.get().put(chunk);
						return new ChunkAndOffset(chunk, pos);
					}
				}
			}

