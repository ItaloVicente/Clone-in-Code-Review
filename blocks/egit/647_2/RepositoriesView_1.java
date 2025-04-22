					RepositoryTreeNode node = (RepositoryTreeNode) ssel
							.getFirstElement();
					if (node.getType() == RepositoryTreeNodeType.REPO
							|| node.getType() == RepositoryTreeNodeType.WORKINGDIR
							|| node.getType() == RepositoryTreeNodeType.FOLDER
							|| node.getType() == RepositoryTreeNodeType.FILE) {
						copyAction.setEnabled(true);
					}
