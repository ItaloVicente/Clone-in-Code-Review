	public long getObjectSize(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		long sz = db.getObjectSize(this
		if (sz < 0) {
			if (typeHint == OBJ_ANY)
				throw new MissingObjectException(objectId.copy()
			throw new MissingObjectException(objectId.copy()
		}
		return sz;
	}

