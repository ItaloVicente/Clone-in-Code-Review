
	@Override
	public boolean isNotLargerThan(AnyObjectId objectId
			long size)
			throws MissingObjectException
			IOException {
		if (last != null && !skipGarbagePack(last)) {
			long sz = last.getIndexedObjectSize(this
			if (sz >= DfsPackFile.OBJ_SIZE_IDX_NOT_INDEXED) {
				return sz <= size;
			}
		}

		PackList packList = db.getPackList();
		long sz = findSizeInObjSizeIndex(packList
		if (sz < -1 && packList.dirty()) {
			sz = findSizeInObjSizeIndex(packList
		}

		if (sz >= DfsPackFile.OBJ_SIZE_IDX_NOT_INDEXED) {
			return sz <= size;
		}

		if (typeHint == OBJ_ANY) {
			throw new MissingObjectException(objectId.copy()
					JGitText.get().unknownObjectType2);
		}
		throw new MissingObjectException(objectId.copy()
	}

	private long findSizeInObjSizeIndex(PackList packList
			AnyObjectId objectId) throws IOException {
		for (DfsPackFile pack : packList.packs) {
			if (pack == last || skipGarbagePack(pack)) {
				continue;
			}
			long sz = pack.getIndexedObjectSize(this
			if (-1 <= sz) {
				last = pack;
				return sz;
			}
		}
		return -2;
	}

