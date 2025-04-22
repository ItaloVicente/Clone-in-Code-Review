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
			return OBJ_SIZE_IDX_NOT_IN_PACK;
		}

		PackObjectSizeIndex sizeIdx = getObjectSizeIdx(ctx);
		if (sizeIdx == null) {
			return OBJ_SIZE_IDX_UNAVAILABLE;
		}

		long sz = sizeIdx.getSize(offset);
		return sz < 0 ? OBJ_SIZE_IDX_NOT_INDEXED : sz;
	}

