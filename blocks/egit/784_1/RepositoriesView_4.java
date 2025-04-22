					for (RepositoryTreeNode<Repository> node: newInput) {
						Repository repo = node.getRepository();
						repo.removeRepositoryChangedListener(RepositoriesView.this);
						repo.addRepositoryChangedListener(RepositoriesView.this);
					}
				} else {
