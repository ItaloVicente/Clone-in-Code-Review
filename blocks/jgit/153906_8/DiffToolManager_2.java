	public Optional<String> getExternalToolFromAttributes(
			final Repository repository
			final String path) throws ToolException {
		try {
			WorkingTreeIterator treeIterator = new FileTreeIterator(repository);
			try (TreeWalk walk = new TreeWalk(repository)) {
				walk.addTree(treeIterator);
				walk.setFilter(new NotIgnoredFilter(0));
				while (walk.next()) {
					String treePath = walk.getPathString();
					if (treePath.equals(path)) {
						Attributes attrs = walk.getAttributes();
						}
					}
					if (walk.isSubtree()) {
						walk.enterSubtree();
					}
				}
				return Optional.empty();
			}

		} catch (RevisionSyntaxException | IOException e) {
			throw new ToolException(e);
		}
	}

	public Set<String> getPredefinedAvailableTools() {
		Map<String
		Set<String> availableTools = new HashSet<>();
		for (Entry<String
			if (elem.getValue().isAvailable()) {
				availableTools.add(elem.getKey());
			}
		}
		return availableTools;
	}

