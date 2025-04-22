	/**
	 * Merges the matching node attributes for an entry path.
	 *
	 * @param node
	 *            the node to scan for matches to entryPath
	 * @param entryPath
	 *            the path to test. The path must be relative to this attribute
	 *            node's own repository path, and in repository path format
	 *            (uses '/' and not '\').
	 * @param isDirectory
	 *            true if the target item is a directory.
	 * @param result
	 *            that will hold the attributes matching this entry path. This
	 *            method will NOT override any existing entry in attributes.
	 */
	protected void mergeAttributes(@Nullable AttributesNode node,
			String entryPath,
			boolean isDirectory, Attributes result) {
		if (node == null)
			return;
		List<AttributesRule> rules = node.getRules();
		ListIterator<AttributesRule> ruleIterator = rules
				.listIterator(rules.size());
		while (ruleIterator.hasPrevious()) {
			AttributesRule rule = ruleIterator.previous();
			if (rule.isMatch(entryPath, isDirectory)) {
				ListIterator<Attribute> attributeIte = rule.getAttributes()
						.listIterator(rule.getAttributes().size());
				while (attributeIte.hasPrevious()) {
					expandMacro(attributeIte.previous(), result);
				}
			}
		}
