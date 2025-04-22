
		if (findProjectFast(currentFile, root.getLocation().toFile())) {
			IContainer container = IteratorService.findContainer(root,
					currentFile);
			if (container != null)
				return new ContainerTreeIterator(this, container);
		}
