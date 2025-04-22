		} else {
			final RepositoryTreeNode workingDir = currentNode;
			if (Arrays.stream(viewer.getFilters()).anyMatch(filter -> !filter
					.select(viewer, workingDir.getParent(), workingDir))) {
				return currentNode.getParent();
			}
