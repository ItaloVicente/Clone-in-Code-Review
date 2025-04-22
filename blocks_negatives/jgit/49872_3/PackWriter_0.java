	/**
	 * A callback to tell caller the count of objects ASAP.
	 *
	 * @since 4.1
	 */
	public interface ObjectCountCallback {
		/**
		 * Invoked when the PackWriter has counted the objects to be written
		 * to pack.
		 * <p>
		 * An ObjectCountCallback can use this information to decide whether
		 * the
		 * {@link #writePack(ProgressMonitor, ProgressMonitor, OutputStream)}
		 * operation should be aborted.
		 * <p>
		 * This callback will be called exactly once.
		 *
		 * @param objectCount
		 *            the count of the objects.
		 * @throws WriteAbortedException
		 *             to indicate that the write operation should be aborted.
		 */
		void setObjectCount(long objectCount) throws WriteAbortedException;
	}

