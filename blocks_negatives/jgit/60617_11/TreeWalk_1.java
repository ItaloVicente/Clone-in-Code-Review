	 * Retrieve the git attributes for the current entry.
	 *
	 * <h4>Git attribute computation</h4>
	 *
	 * <ul>
	 * <li>Get the attributes matching the current path entry from the info file
	 * (see {@link AttributesNodeProvider#getInfoAttributesNode()}).</li>
	 * <li>Completes the list of attributes using the .gitattributes files
	 * located on the current path (the further the directory that contains
	 * .gitattributes is from the path in question, the lower its precedence).
	 * For a checkin operation, it will look first on the working tree (if any).
	 * If there is no attributes file, it will fallback on the index. For a
	 * checkout operation, it will first use the index entry and then fallback
	 * on the working tree if none.</li>
	 * <li>In the end, completes the list of matching attributes using the
	 * global attribute file define in the configuration (see
	 * {@link AttributesNodeProvider#getGlobalAttributesNode()})</li>
	 *
	 * </ul>
	 *
	 *
	 * <h4>Iterator constraints</h4>
	 *
	 * <p>
	 * In order to have a correct list of attributes for the current entry, this
	 * {@link TreeWalk} requires to have at least one
	 * {@link AttributesNodeProvider} and a {@link DirCacheIterator} set up. An
	 * {@link AttributesNodeProvider} is used to retrieve the attributes from
	 * the info attributes file and the global attributes file. The
	 * {@link DirCacheIterator} is used to retrieve the .gitattributes files
	 * stored in the index. A {@link WorkingTreeIterator} can also be provided
	 * to access the local version of the .gitattributes files. If none is
	 * provided it will fallback on the {@link DirCacheIterator}.
	 * </p>
	 *
	 * @return a {@link Set} of {@link Attribute}s that match the current entry.
	 * @since 4.2
	 */
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
