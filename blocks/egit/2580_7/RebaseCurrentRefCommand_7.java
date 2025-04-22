				if (sel.getFirstElement() instanceof RepositoryTreeNode) {
					Repository repo = ((RepositoryTreeNode) ((IStructuredSelection) selection)
							.getFirstElement()).getRepository();
					boolean isSafe = repo.getRepositoryState() == RepositoryState.SAFE;

					setBaseEnabled(isSafe && hasHead(repo));
				}
