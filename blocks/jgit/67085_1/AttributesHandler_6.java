	private static List<TranslatedAttributesRule> rulesInReverseOrder(
			String declarationPath
			@Nullable AttributesNode node) {
		if (node == null)
			return Collections.emptyList();
		ArrayList<TranslatedAttributesRule> rules = new ArrayList<>();
		for (AttributesRule rule : node.getRules()) {
			rules.add(new TranslatedAttributesRule(declarationPath
		}
		Collections.reverse(rules);
		return rules;
	}

	private static class CacheEntry {
		private final String path;

		private final Map<String

		private final List<TranslatedAttributesRule> infoRules;

		private final List<TranslatedAttributesRule> dirRules;

		private final List<TranslatedAttributesRule> globalRules;

		CacheEntry(TreeWalk treeWalk) throws IOException {
			path = ROOT_PATH;

			AttributesNodeProvider attributesNodeProvider = treeWalk
					.getAttributesNodeProvider();

			AttributesNode globalNode = attributesNodeProvider != null
					? attributesNodeProvider.getGlobalAttributesNode() : null;
			AttributesNode infoNode = attributesNodeProvider != null
					? attributesNodeProvider.getInfoAttributesNode() : null;
			AttributesNode dirNode = attributesNode(treeWalk
					rootOf(treeWalk.getTree(WorkingTreeIterator.class))
					rootOf(treeWalk.getTree(DirCacheIterator.class))
					rootOf(treeWalk.getTree(CanonicalTreeParser.class)));

			macroExpansions = new HashMap<>();
			macroExpansions.put(BINARY_RULE_KEY
			for (AttributesNode node : new AttributesNode[] { globalNode
					dirNode
				if (node == null) {
					continue;
				}
				for (AttributesRule rule : node.getRules()) {
					if (rule.getPattern().startsWith(MACRO_PREFIX)) {
						macroExpansions.put(rule.getPattern()
								.substring(MACRO_PREFIX.length()).trim()
								rule.getAttributes());
					}
				}
			}

			infoRules = rulesInReverseOrder(path
			dirRules = rulesInReverseOrder(path
			globalRules = rulesInReverseOrder(path
		}

		CacheEntry(CacheEntry parentEntry
				AttributesNode subFolderNode) {
			this.path = path;
			macroExpansions = parentEntry.macroExpansions;
			infoRules = parentEntry.infoRules;
			globalRules = parentEntry.globalRules;
			List<TranslatedAttributesRule> newDirRules = rulesInReverseOrder(
					path
			if (newDirRules.isEmpty()) {
				dirRules = parentEntry.dirRules;
			} else {
				newDirRules.addAll(parentEntry.dirRules);
				dirRules = newDirRules;
			}
		}

	}

