	private PackObjectSizeIndex getObjectSizeIdx(DfsReader ctx) {
		if (objSizeIdx != null) {
			return objSizeIdx;
		}

		if (objSizeIdxLoadAttempted) {
			return null;
		}

		DfsStreamKey objSizeKey = desc.getStreamKey(OBJECT_SIZE_INDEX);
		AtomicBoolean cacheHit = new AtomicBoolean(true);
		try {
			DfsBlockCache.Ref<PackObjectSizeIndex> sizeIdxRef = cache
					.getOrLoadRef(objSizeKey
						cacheHit.set(false);
						return loadObjectSizeIdx(ctx
					});
			if (cacheHit.get()) {
				ctx.stats.objSizeCacheHit++;
			}
			PackObjectSizeIndex sizeIdx = sizeIdxRef.get();
			if (objSizeIdx == null && sizeIdx != null) {
				objSizeIdx = sizeIdx;
			}
		} catch (IOException e) {
		} finally {
			objSizeIdxLoadAttempted = true;
		}

		return objSizeIdx;
	}

