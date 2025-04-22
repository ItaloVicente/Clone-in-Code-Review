			if (deleteWorkDir
					&& !repo.isBare()
					&& node.getParent() != null
					&& node.getParent().getType() == RepositoryTreeNodeType.SUBMODULES) {
				FileUtils.delete(repo.getWorkTree(), FileUtils.RECURSIVE
						| FileUtils.RETRY | FileUtils.SKIP_MISSING);
				node.getParent().getRepository().notifyIndexChanged();
