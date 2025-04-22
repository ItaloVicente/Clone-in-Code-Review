	 * @since 6.1
	 */
	protected void addToCheckout(String path, DirCacheEntry entry,
			Attributes[] attributes)
			throws IOException {
		toBeCheckedOut.put(path, entry);
		addCheckoutMetadata(cleanupMetadata, path, attributes[T_OURS]);
		addCheckoutMetadata(checkoutMetadata, path, attributes[T_THEIRS]);
	}

	/**
