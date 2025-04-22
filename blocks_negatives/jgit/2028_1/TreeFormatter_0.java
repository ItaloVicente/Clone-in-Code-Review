	/**
	 * Compute the current tree's ObjectId.
	 *
	 * @return computed ObjectId of the tree
	 */
	public ObjectId getTreeId() {
		final ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		if (buf != null)
			return fmt.idFor(OBJ_TREE, buf, 0, ptr);

		try {
			final long len = overflowBuffer.length();
			return fmt.idFor(OBJ_TREE, len, overflowBuffer.openInputStream());
		} catch (IOException err) {
			throw new RuntimeException(err);
		}
	}

