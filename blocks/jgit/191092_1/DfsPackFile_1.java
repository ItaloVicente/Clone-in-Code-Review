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
			return -2;
		}

		PackObjectSizeIndex sizeIdx = getObjectSizeIdx(ctx);
		if (sizeIdx == null) {
			return -3;
		}

		long sz = sizeIdx.getSize(offset);
		return sz < 0 ? -1 : sz;
	}

