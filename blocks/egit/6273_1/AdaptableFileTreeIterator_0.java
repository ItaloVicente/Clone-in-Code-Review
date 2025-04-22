
		final File dotProject = new File(currentFile, ".project"); //$NON-NLS-1$
		if (dotProject.exists()) {
			IContainer container = IteratorService.findContainer(root,
					currentFile);
			if (container != null)
				return new ContainerTreeIterator(this, container);
		}
