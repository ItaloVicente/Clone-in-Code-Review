		ImageDescriptor base = getBaseImageDescriptor(node);
		if (base == null) {
			return null;
		}
		try {
			return decorateImageDescriptor(base, node);
		} catch (IOException e) {
			return base;
		}
	}

	private ImageDescriptor getBaseImageDescriptor(
			@NonNull RepositoryTreeNode<?> node) {
