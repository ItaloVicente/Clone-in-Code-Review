			if (ssel.getFirstElement() instanceof IAdaptable) {
				IResource adapted = (IResource) ((IAdaptable) ssel
						.getFirstElement()).getAdapter(IResource.class);
				if (adapted != null) {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(adapted);
					if (mapping != null)
						repository = mapping.getRepository();
				}
			} else if (ssel.getFirstElement() instanceof RepositoryTreeNode) {
				RepositoryTreeNode repoNode = (RepositoryTreeNode) ssel
						.getFirstElement();
				repository = repoNode.getRepository();
