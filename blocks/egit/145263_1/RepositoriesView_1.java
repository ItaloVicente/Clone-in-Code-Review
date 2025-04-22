				RepositoryTreeNode node = getRepositoryChildNode(repository,
						RepositoryTreeNodeType.WORKINGDIR);
				if (node == null) {
					boolean newOne = repositoryUtil
							.addConfiguredRepository(repository.getDirectory());
					if (newOne) {
						added = true;
					}
