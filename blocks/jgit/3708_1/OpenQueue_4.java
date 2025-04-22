				if (prefetcher == null) {
					int limit = reader.getOptions().getChunkLimit();
					int ratio = reader.getOptions().getOpenQueuePrefetchRatio();
					int prefetchLimit = (int) (limit * (ratio / 100.0));
					reader.getRecentChunks().setMaxBytes(limit - prefetchLimit);
					prefetcher = new Prefetcher(reader
				}
