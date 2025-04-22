		if (object instanceof RepositoryTreeNode) {
			RepositoryTreeNodeType nodeType = ((RepositoryTreeNode) object)
					.getType();
			boolean unsupportedNodeType = EnumSet.of(
					RepositoryTreeNodeType.ADDITIONALREFS,
					RepositoryTreeNodeType.BRANCHES,
					RepositoryTreeNodeType.BRANCHHIERARCHY,
					RepositoryTreeNodeType.ERROR, RepositoryTreeNodeType.FETCH,
					RepositoryTreeNodeType.LOCAL, RepositoryTreeNodeType.PUSH,
					RepositoryTreeNodeType.REMOTE,
					RepositoryTreeNodeType.REMOTES,
					RepositoryTreeNodeType.REMOTETRACKING,
					RepositoryTreeNodeType.STASH,
					RepositoryTreeNodeType.STASHED_COMMIT,
					RepositoryTreeNodeType.TAGS)
					.contains(nodeType);
			return !unsupportedNodeType;
		}
