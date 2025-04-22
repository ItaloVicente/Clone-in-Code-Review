	@Override
	public boolean isSmallerThan(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		if (!db.has(objectId)) {
			throw new MissingObjectException(
					AbbreviatedObjectId.fromObjectId(objectId)
		}

		if (typeHint != Constants.OBJ_BLOB) {
			return db.getObjectSize(this
		}

		Pack pack = findPack(objectId);
		if (pack == null) {
			return db.getObjectSize(this
		}

		return pack.getIndexedObjectSize(objectId) <= size;
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

