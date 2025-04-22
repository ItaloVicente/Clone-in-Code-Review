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
		long idxPos = idx().findPosition(id);
		if (idxPos < 0) {
			return -1;
		}

		PackObjectSizeIndex sizeIdx = objSizeIdx();
		if (sizeIdx == null) {
			throw new IllegalStateException(
		}

		return sizeIdx.getSize(idxPos);
	}

