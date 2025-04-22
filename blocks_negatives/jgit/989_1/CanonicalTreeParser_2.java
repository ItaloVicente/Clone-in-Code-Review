		final ObjectLoader ldr = repo.openObject(curs, id);
		if (ldr == null) {
			final ObjectId me = id.toObjectId();
			throw new MissingObjectException(me, Constants.TYPE_TREE);
		}
		final byte[] subtreeData = ldr.getCachedBytes();
		if (ldr.getType() != Constants.OBJ_TREE) {
			final ObjectId me = id.toObjectId();
			throw new IncorrectObjectTypeException(me, Constants.TYPE_TREE);
		}
		reset(subtreeData);
