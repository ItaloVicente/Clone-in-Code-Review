				RepositoryTreeNode currentNode = null;
				ITreeContentProvider cp = (ITreeContentProvider) getCommonViewer()
						.getContentProvider();
				for (Object repo : cp.getElements(getCommonViewer().getInput())) {
					RepositoryTreeNode node = (RepositoryTreeNode) repo;
					if (mapping.getRepository().getDirectory().equals(
							((Repository) node.getObject()).getDirectory())) {
						for (Object child : cp.getChildren(node)) {
							RepositoryTreeNode childNode = (RepositoryTreeNode) child;
							if (childNode.getType() == RepositoryTreeNodeType.WORKINGDIR) {
								currentNode = childNode;
								break;
							}
						}
						break;
					}
				}
