		DirCacheEntry newEntry = new DirCacheEntry(e.getRawPath(),
				e.getStage());
		newEntry.setFileMode(e.getFileMode());
		newEntry.setObjectId(e.getObjectId());
		newEntry.setLastModified(e.getLastModifiedInstant());
		newEntry.setLength(e.getLength());
		builder.add(newEntry);
		return newEntry;
	}

	/**
	 * Remembers the {@link CheckoutMetadata} for the given path; it may be
	 * needed in {@link #checkout()} or in {@link #cleanUp()}.
	 *
	 * @param map
	 *            to add the metadata to
	 * @param path
	 *            of the current node
	 * @param attributes
	 *            to use for determining the metadata
	 * @throws IOException
	 *             if the smudge filter cannot be determined
	 * @since 6.1
	 */
	protected void addCheckoutMetadata(Map<String, CheckoutMetadata> map,
			String path, Attributes attributes)
			throws IOException {
		if (map != null) {
			EolStreamType eol = EolStreamTypeUtil.detectStreamType(
					OperationType.CHECKOUT_OP, workingTreeOptions,
					attributes);
			CheckoutMetadata data = new CheckoutMetadata(eol,
					tw.getSmudgeCommand(attributes));
			map.put(path, data);
		}
