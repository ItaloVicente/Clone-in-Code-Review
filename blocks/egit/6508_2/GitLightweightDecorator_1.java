		if (element instanceof RepositoryNode)
			try {
				decorateRepository((RepositoryNode) element, decoration);
				return;
			} catch (CoreException e) {
				exceptions.handleException(e);
				return;
			}

