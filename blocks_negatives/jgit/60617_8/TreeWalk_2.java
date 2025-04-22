	public Attributes getAttributes() {
		if (attrs != null)
			return attrs;

		if (attributesNodeProvider == null) {
			throw new IllegalStateException(
		}

		WorkingTreeIterator workingTreeIterator = getTree(WorkingTreeIterator.class);
		DirCacheIterator dirCacheIterator = getTree(DirCacheIterator.class);
		CanonicalTreeParser other = getTree(CanonicalTreeParser.class);

		if (workingTreeIterator == null && dirCacheIterator == null
				&& other == null) {
			return new Attributes();
		}

		String path = currentHead.getEntryPathString();
		final boolean isDir = FileMode.TREE.equals(currentHead.mode);
		Attributes attributes = new Attributes();
		try {
			AttributesNode globalNodeAttr = attributesNodeProvider
					.getGlobalAttributesNode();
			AttributesNode infoNodeAttr = attributesNodeProvider
					.getInfoAttributesNode();

			if (infoNodeAttr != null) {
				infoNodeAttr.getAttributes(path, isDir, attributes);
			}

			getPerDirectoryEntryAttributes(path, isDir, operationType,
					workingTreeIterator, dirCacheIterator, other, attributes);

			if (globalNodeAttr != null) {
				globalNodeAttr.getAttributes(path, isDir, attributes);
			}
		} catch (IOException e) {
			throw new JGitInternalException("Error while parsing attributes", e); //$NON-NLS-1$
		}
		for (Attribute a : attributes.getAll()) {
			if (a.getState() == State.UNSPECIFIED)
				attributes.remove(a.getKey());
		}
		return attributes;
	}

	/**
	 * Get the attributes located on the current entry path.
	 *
	 * @param path
	 *            current entry path
	 * @param isDir
	 *            holds true if the current entry is a directory
	 * @param opType
	 *            type of operation
	 * @param workingTreeIterator
	 *            a {@link WorkingTreeIterator} matching the current entry
	 * @param dirCacheIterator
	 *            a {@link DirCacheIterator} matching the current entry
	 * @param other
	 *            a {@link CanonicalTreeParser} matching the current entry
	 * @param attributes
	 *            Non null map holding the existing attributes. This map will be
	 *            augmented with new entry. None entry will be overrided.
	 * @throws IOException
	 *             It raises an {@link IOException} if a problem appears while
	 *             parsing one on the attributes file.
	 */
	private void getPerDirectoryEntryAttributes(String path, boolean isDir,
			OperationType opType, WorkingTreeIterator workingTreeIterator,
			DirCacheIterator dirCacheIterator, CanonicalTreeParser other,
			Attributes attributes)
			throws IOException {
		if (workingTreeIterator != null || dirCacheIterator != null
				|| other != null) {
			AttributesNode currentAttributesNode = getCurrentAttributesNode(
					opType, workingTreeIterator, dirCacheIterator, other);
			if (currentAttributesNode != null) {
				currentAttributesNode.getAttributes(path, isDir, attributes);
			}
			getPerDirectoryEntryAttributes(path, isDir, opType,
					getParent(workingTreeIterator, WorkingTreeIterator.class),
					getParent(dirCacheIterator, DirCacheIterator.class),
					getParent(other, CanonicalTreeParser.class), attributes);
		}
	}

	private static <T extends AbstractTreeIterator> T getParent(T current,
