		ImageDescriptor base = getBaseImageDescriptor(node);
		if (base == null) {
			return null;
		}
		return decorateImageDescriptor(base, node);
	}

	private ImageDescriptor getBaseImageDescriptor(
			@NonNull RepositoryTreeNode<?> node) {
