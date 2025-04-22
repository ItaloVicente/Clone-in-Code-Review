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
		if (0 < offset && !isCorrupt(offset)) {
			return -2;
		}

		PackObjectSizeIndex sizeIdx = objSizeIdx();
		if (sizeIdx == null) {
			return -3;
		}

		long sz = sizeIdx.getSize(offset);
		return sz < 0 ? -1 : sz;
	}

