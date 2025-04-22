	/**
	 * Mark object database as dirty.
	 * <p>
	 * Used when the caller knows that new data might have been written to the
	 * repository that could invalidate open readers, for example if refs are
	 * newly scanned.
	 */
	protected void markDirty() {
		packList.get().markDirty();
	}

