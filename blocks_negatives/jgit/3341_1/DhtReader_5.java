			/**
			 * Number of times the prefetcher obtained from {@link ChunkCache}.
			 * Incremented when the prefetcher recovered the chunk from the
			 * local JVM chunk cache and thus avoided reading the database.
			 */
			public int cntPrefetcher_ChunkCacheHit;

