	@Override
	public long getObjectSize(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		if (typeHint != Constants.OBJ_BLOB) {
			return getObjectSizeStorage(objectId
		}

		Pack pack = findPack(objectId);
		if (pack == null) {
			return getObjectSizeStorage(objectId
		}

		long sz = pack.getIndexedObjectSize(objectId);
		if (sz > 0) {
			return sz;
		}
		return getObjectSizeStorage(objectId
	}

	@Override
	public boolean isNotLargerThan(AnyObjectId objectId
			long size)
			throws MissingObjectException
			IOException {
		if (typeHint != Constants.OBJ_BLOB) {
			return getObjectSizeStorage(objectId
		}

		Pack pack = findPack(objectId);
		if (pack == null || !pack.hasObjSizeIndex()) {
			return getObjectSizeStorage(objectId
		}

		long sz = pack.getIndexedObjectSize(objectId);
		if (sz == Pack.OBJ_SIZE_IDX_NOT_IN_PACK) {
		}
		return sz <= size;
	}

	private Pack findPack(AnyObjectId objectId) throws IOException {
		if (lastPack != null && lastPack.hasObject(objectId)) {
			return lastPack;
		}

		for (Pack p : db.getPacks()) {
			if (p.hasObject(objectId)) {
				lastPack = p;
				return p;
			}
		}

		return null;
	}

