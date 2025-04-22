		final ObjectLoader ldr = walk.db.openObject(walk.curs, this);
		if (ldr == null)
			throw new MissingObjectException(this, getType());
		final byte[] data = ldr.getCachedBytes();
		if (getType() != ldr.getType())
			throw new IncorrectObjectTypeException(this, getType());
		return data;
