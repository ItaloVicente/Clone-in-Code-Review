	/**
	 * Merge together two tree-ish objects.
	 * <p>
	 * Any tree-ish may be supplied as inputs. Commits and/or tags pointing at
	 * trees or commits may be passed as input objects.
	 *
	 * @param a
	 *            source tree to be combined together.
	 * @param b
	 *            source tree to be combined together.
	 * @return true if the merge was completed without conflicts; false if the
	 *         merge strategy cannot handle this merge or there were conflicts
	 *         preventing it from automatically resolving all paths.
	 * @throws IncorrectObjectTypeException
	 *             one of the input objects is not a commit, but the strategy
	 *             requires it to be a commit.
	 * @throws IOException
	 *             one or more sources could not be read, or outputs could not
	 *             be written to the Repository.
	 */
	public boolean merge(final AnyObjectId a, final AnyObjectId b)
			throws IOException {
		return merge(new AnyObjectId[] { a, b });
	}

