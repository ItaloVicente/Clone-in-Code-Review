			PackChunk chunk = cache.get(key);

			if (chunk != null) {
				stats.access(key).cntPrefetcher_ChunkCacheHit++;
				chunkIsReady(chunk);
			} else {
				stats.access(key).cntPrefetcher_Load++;
				toLoad.add(key);
				status.put(key, Status.LOADING);
				bytesLoading += averageChunkSize;

				if (first)
					break;
			}
