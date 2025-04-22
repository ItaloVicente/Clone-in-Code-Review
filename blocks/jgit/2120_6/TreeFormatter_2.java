	public ObjectId computeId(ObjectInserter ins) {
		if (buf != null)
			return ins.idFor(OBJ_TREE

		final long len = overflowBuffer.length();
		try {
			return ins.idFor(OBJ_TREE
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

