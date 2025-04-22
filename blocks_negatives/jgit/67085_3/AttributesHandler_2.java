		AttributesNodeProvider attributesNodeProvider =treeWalk.getAttributesNodeProvider();
		this.globalNode = attributesNodeProvider != null
				? attributesNodeProvider.getGlobalAttributesNode() : null;
		this.infoNode = attributesNodeProvider != null
				? attributesNodeProvider.getInfoAttributesNode() : null;

		AttributesNode rootNode = attributesNode(treeWalk,
				rootOf(
						treeWalk.getTree(WorkingTreeIterator.class)),
				rootOf(
						treeWalk.getTree(DirCacheIterator.class)),
				rootOf(treeWalk
						.getTree(CanonicalTreeParser.class)));

		expansions.put(BINARY_RULE_KEY, BINARY_RULE_ATTRIBUTES);
		for (AttributesNode node : new AttributesNode[] { globalNode, rootNode,
				infoNode }) {
			if (node == null) {
				continue;
			}
			for (AttributesRule rule : node.getRules()) {
				if (rule.getPattern().startsWith(MACRO_PREFIX)) {
					expansions.put(rule.getPattern()
							.substring(MACRO_PREFIX.length()).trim(),
							rule.getAttributes());
				}
			}
		}
