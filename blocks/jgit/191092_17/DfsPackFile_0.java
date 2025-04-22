	private PackObjectSizeIndex getObjectSizeIndex(DfsReader ctx)
			throws IOException {
		if (objectSizeIndex != null) {
			return objectSizeIndex;
		}

		if (objectSizeIndexLoadAttempted) {
			return null;
		}

		DfsStreamKey objSizeKey = desc.getStreamKey(OBJECT_SIZE_INDEX);
		AtomicBoolean cacheHit = new AtomicBoolean(true);
		try {
			DfsBlockCache.Ref<PackObjectSizeIndex> sizeIdxRef = cache
					.getOrLoadRef(objSizeKey
						cacheHit.set(false);
						return loadObjectSizeIndex(ctx
					});
			if (cacheHit.get()) {
				ctx.stats.objectSizeIndexCacheHit++;
			}
			PackObjectSizeIndex sizeIdx = sizeIdxRef.get();
			if (objectSizeIndex == null && sizeIdx != null) {
				objectSizeIndex = sizeIdx;
			}
		} finally {
			objectSizeIndexLoadAttempted = true;
		}

		return objectSizeIndex;
	}

