			Object firstElement = ssel.getFirstElement();
			if (firstElement instanceof IResource)
				showResource((IResource) firstElement);
			else if (firstElement instanceof RepositoryTreeNode) {
				RepositoryTreeNode repoNode = (RepositoryTreeNode) firstElement;
				reload(repoNode.getRepository());
			} else if (firstElement instanceof IAdaptable) {
				IResource adapted = (IResource) ((IAdaptable) firstElement).getAdapter(IResource.class);
