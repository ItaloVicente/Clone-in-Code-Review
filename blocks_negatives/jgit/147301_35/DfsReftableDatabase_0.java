	/**
	 * Obtain a handle to the merged reader.
	 *
	 * @return (possibly cached) handle to the merged reader.
	 * @throws java.io.IOException
	 *             if tables cannot be opened.
	 */
	protected Reftable reader() throws IOException {
		lock.lock();
		try {
			if (mergedTables == null) {
				mergedTables = new MergedReftable(stack().readers());
			}
			return mergedTables;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Obtain a handle to the stack of reftables.
	 *
	 * @return (possibly cached) handle to the stack.
	 * @throws java.io.IOException
	 *             if tables cannot be opened.
	 */
	protected DfsReftableStack stack() throws IOException {
		lock.lock();
		try {
			if (tableStack == null) {
				DfsObjDatabase odb = getRepository().getObjectDatabase();
				if (ctx == null) {
					ctx = odb.newReader();
				}
				tableStack = DfsReftableStack.open(ctx,
						Arrays.asList(odb.getReftables()));
			}
			return tableStack;
		} finally {
			lock.unlock();
		}
	}

	/** {@inheritDoc} */
