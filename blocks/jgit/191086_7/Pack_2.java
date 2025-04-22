	public boolean hasObjSizeIndex() throws IOException {
		return objSizeIdx() != null;
	}

	public long getObjectCountSizeIndex() throws IOException {
		if (!hasObjSizeIndex()) {
			return 0;
		}

		return objSizeIdx().getObjectCount();
	}

	public long getIndexedObjectSize(AnyObjectId id) throws IOException {
		final long offset = idx().findOffset(id);
		if (offset < 0 || isCorrupt(offset)) {
			return OBJ_SIZE_IDX_NOT_IN_PACK;
		}

		PackObjectSizeIndex sizeIdx = objSizeIdx();
		if (sizeIdx == null) {
			return OBJ_SIZE_IDX_UNAVAILABLE;
		}

		long sz = sizeIdx.getSize(offset);
		return sz < 0 ? OBJ_SIZE_IDX_NOT_INDEXED : sz;
	}

