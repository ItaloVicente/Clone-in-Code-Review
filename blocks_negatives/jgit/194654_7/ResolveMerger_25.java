	 * @return Whether the trees merged cleanly.
	 * @throws java.io.IOException
	 * @since 3.5
	 */
	protected boolean mergeTreeWalk(TreeWalk treeWalk, boolean ignoreConflicts)
			throws IOException {
		boolean hasWorkingTreeIterator = tw.getTreeCount() > T_FILE;
		boolean hasAttributeNodeProvider = treeWalk
				.getAttributesNodeProvider() != null;
		while (treeWalk.next()) {
			Attributes[] attributes = { NO_ATTRIBUTES, NO_ATTRIBUTES,
					NO_ATTRIBUTES };
			if (hasAttributeNodeProvider) {
				attributes[T_BASE] = treeWalk.getAttributes(T_BASE);
				attributes[T_OURS] = treeWalk.getAttributes(T_OURS);
				attributes[T_THEIRS] = treeWalk.getAttributes(T_THEIRS);
			}
			if (!processEntry(
					treeWalk.getTree(T_BASE, CanonicalTreeParser.class),
					treeWalk.getTree(T_OURS, CanonicalTreeParser.class),
					treeWalk.getTree(T_THEIRS, CanonicalTreeParser.class),
					treeWalk.getTree(T_INDEX, DirCacheBuildIterator.class),
					hasWorkingTreeIterator ? treeWalk.getTree(T_FILE,
							WorkingTreeIterator.class) : null,
					ignoreConflicts, attributes)) {
				cleanUp();
				return false;
			}
			if (treeWalk.isSubtree() && enterSubtree)
				treeWalk.enterSubtree();
		}
		return true;
	}
