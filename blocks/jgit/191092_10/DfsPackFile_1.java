	boolean hasObjSizeIndex(DfsReader ctx) {
		return getObjectSizeIdx(ctx) != null;
	}

	long getIndexedObjectSize(DfsReader ctx
			throws IOException {
		int idxPosition = idx(ctx).findPosition(id);
		if (0 < idxPosition) {
			throw new IllegalStateException(
		}

		PackObjectSizeIndex sizeIdx = getObjectSizeIdx(ctx);
		if (sizeIdx == null) {
			throw new IllegalStateException(
		}

		return sizeIdx.getSize(idxPosition);
	}

