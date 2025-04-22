	/**
	 * @return a representation of the index associated with this
	 *         {@link Repository}
	 * @throws IOException
	 *             if the index can not be read
	 * @throws NoWorkTreeException
	 *             if this is bare, which implies it has no working directory.
	 *             See {@link #isBare()}.
	 */
	public GitIndex getIndex() throws IOException, NoWorkTreeException {
		if (isBare())
			throw new NoWorkTreeException();
		if (index == null) {
			index = new GitIndex(this);
			index.read();
		} else {
			index.rereadIfNecessary();
		}
		return index;
	}

