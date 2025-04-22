	private String getRefName(final RepositoryTreeNode node) {
		Object object = node.getObject();
		if (!(object instanceof Ref))
			return null;

		Ref ref = (Ref) object;
		return ref.getName();
	}

