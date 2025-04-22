		return new AdaptableFileTreeIterator(repository, ResourcesPlugin.getWorkspace().getRoot());
	}

	public static IContainer findContainer(File file) {
		if (!file.isDirectory())
			throw new IllegalArgumentException(
					"file " + file.getAbsolutePath() + " is no directory"); //$NON-NLS-1$//$NON-NLS-2$
		final IContainer container = ProjectUtil.findContainer(file);
		if (container.isAccessible() && isProjectSharedWithGit(container))
			return container;
		return null;
