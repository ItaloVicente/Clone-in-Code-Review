			if (workTree != null) {
				if (node.getParent() != null
						&& node.getParent().getType() == RepositoryTreeNodeType.SUBMODULES) {
					FileUtils.delete(workTree, FileUtils.RECURSIVE
							| FileUtils.RETRY | FileUtils.SKIP_MISSING);
					node.getParent().getRepository().notifyIndexChanged();
				}
				String[] files = workTree.list();
				boolean isWorkingDirEmpty = files != null && files.length == 0;
				if (isWorkingDirEmpty)
					FileUtils.delete(workTree, FileUtils.RETRY | FileUtils.SKIP_MISSING);
