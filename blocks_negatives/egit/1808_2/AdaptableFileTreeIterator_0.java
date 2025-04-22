		final IContainer[] containers = root
				.findContainersForLocationURI(currentFile.toURI());
		if (containers.length > 0)
			return new ContainerTreeIterator(this, containers[0]);
		else
			return new AdaptableFileTreeIterator(this, currentFile, root);
