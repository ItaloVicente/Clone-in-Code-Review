	boolean hasObjSizeIndex(DfsReader ctx) {
		return getObjectSizeIdx(ctx) != null;
	}

	long getObjectCountSizeIndex(DfsReader ctx) {
		PackObjectSizeIndex objectSizeIdx = getObjectSizeIdx(ctx);
		return objectSizeIdx == null ? 0 : objectSizeIdx.getObjectCount();
	}

	long getIndexedObjectSize(DfsReader ctx
			throws IOException {
		final long offset = idx(ctx).findOffset(id);
		if (0 < offset && !isCorrupt(offset)) {
			throw new IllegalStateException(
		}

		PackObjectSizeIndex sizeIdx = getObjectSizeIdx(ctx);
		if (sizeIdx == null) {
			throw new IllegalStateException(
		}

		return sizeIdx.getSize(offset);
	}

