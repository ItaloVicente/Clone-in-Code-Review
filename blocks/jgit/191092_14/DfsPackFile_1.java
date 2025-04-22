	boolean hasObjectSizeIndex(DfsReader ctx) {
		try {
			return getObjectSizeIndex(ctx) != null;
		} catch (IOException e) {
			return false;
		}
	}

	long getIndexedObjectSize(DfsReader ctx
			throws IOException {
		int idxPosition = idx(ctx).findPosition(id);
		if (idxPosition < 0) {
			throw new IllegalStateException(
		}

		PackObjectSizeIndex sizeIdx = getObjectSizeIndex(ctx);
		if (sizeIdx == null) {
			throw new IllegalStateException(
		}

		return sizeIdx.getSize(idxPosition);
	}

