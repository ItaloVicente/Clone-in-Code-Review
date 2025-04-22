		if (object instanceof IResource) {
			return typeOk((IResource) object);
		}

		if (object instanceof RepositoryTreeNode)
			return true;

