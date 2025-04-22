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
