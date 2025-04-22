
	@Override
	public boolean isNotLargerThan(AnyObjectId objectId
			long size)
			throws MissingObjectException
			IOException {
		DfsPackFile pack = findPackWithObject(objectId);
		if (pack == null) {
			if (typeHint == OBJ_ANY) {
				throw new MissingObjectException(objectId.copy()
						JGitText.get().unknownObjectType2);
			}
			throw new MissingObjectException(objectId.copy()
		}

		stats.isNotLargerThanCallCount += 1;
		if (typeHint != Constants.OBJ_BLOB || !pack.hasObjSizeIndex(this)) {
			return pack.getObjectSize(this
		}

		long sz = pack.getIndexedObjectSize(this
		if (sz < 0) {
			stats.objSizeIndexMiss += 1;
		} else {
			stats.objSizeIndexHit += 1;
		}

		return sz <= size;
	}

	private DfsPackFile findPackWithObject(AnyObjectId objectId)
			throws IOException {
		if (last != null && !skipGarbagePack(last) && last.hasObject(this
			return last;
		}
		PackList packList = db.getPackList();
		if (hasImpl(packList
			return last;
		} else if (packList.dirty()) {
			if (hasImpl(db.getPackList()
				return last;
			}
		}
		return null;
	}

