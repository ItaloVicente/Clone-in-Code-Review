	 * Prepares the bitmaps to be written to the pack index. Bitmaps can be used
	 * to speed up fetches and clones by storing the entire object graph at
	 * selected commits.
	 *
	 * This method can only be invoked after
	 * {@link #writePack(ProgressMonitor, ProgressMonitor, OutputStream)} has
	 * been invoked and completed successfully. Writing a corresponding bitmap
	 * index is an optional feature that not all pack users may require.
