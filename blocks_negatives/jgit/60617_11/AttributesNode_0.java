	/**
	 * Returns the matching attributes for an entry path.
	 *
	 * @param entryPath
	 *            the path to test. The path must be relative to this attribute
	 *            node's own repository path, and in repository path format
	 *            (uses '/' and not '\').
	 * @param isDirectory
	 *            true if the target item is a directory.
	 * @param attributes
	 *            Map that will hold the attributes matching this entry path. If
	 *            it is not empty, this method will NOT override any existing
	 *            entry.
	 * @since 4.2
	 */
	public void getAttributes(String entryPath,
			boolean isDirectory, Attributes attributes) {
		ListIterator<AttributesRule> ruleIterator = rules.listIterator(rules
				.size());
		while (ruleIterator.hasPrevious()) {
			AttributesRule rule = ruleIterator.previous();
			if (rule.isMatch(entryPath, isDirectory)) {
				ListIterator<Attribute> attributeIte = rule.getAttributes()
						.listIterator(rule.getAttributes().size());
				while (attributeIte.hasPrevious()) {
					Attribute attr = attributeIte.previous();
					if (!attributes.containsKey(attr.getKey()))
						attributes.put(attr);
				}
			}
		}
	}
