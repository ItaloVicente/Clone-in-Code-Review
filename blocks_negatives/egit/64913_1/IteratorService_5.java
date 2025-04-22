		File workTree = repository.getWorkTree();
		if (!workTree.exists())
			return null;
		IContainer container = findContainer(root, workTree);
		if (container != null)
			return new ContainerTreeIterator(repository, container);
		return new AdaptableFileTreeIterator(repository, root);
