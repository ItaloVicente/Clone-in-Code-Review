
	@Override
	public boolean isNotLargerThan(AnyObjectId objectId
			long size)
			throws MissingObjectException
			IOException {
		DfsPackFile pack = findPackWithObject(objectId);
		if (pack != null) {
			long sz = typeHint == Constants.OBJ_BLOB && pack.hasObjSizeIndex(
					this)
					? pack.getIndexedObjectSize(this
					: pack.getObjectSize(this
			return sz <= size;
		}

		if (typeHint == OBJ_ANY) {
			throw new MissingObjectException(objectId.copy()
					JGitText.get().unknownObjectType2);
		}
		throw new MissingObjectException(objectId.copy()
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

