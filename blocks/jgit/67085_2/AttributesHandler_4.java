		boolean isDirectory = (treeWalk.getFileMode() == FileMode.TREE);
		Attributes attributes = new Attributes();
		for (List<TranslatedAttributesRule> rules : Arrays.asList(
				cacheEntry.infoRules
				cacheEntry.dirRules
			for (TranslatedAttributesRule rule : rules) {
				if (rule.isMatch(entryPath
					ListIterator<Attribute> attributeIte = rule.getAttributes()
							.listIterator(rule.getAttributes().size());
					while (attributeIte.hasPrevious()) {
						expandMacro(attributeIte.previous()
					}
				}
			}
		}
