	private Runnable refschangedRunnable;

	public static boolean canShowHistoryFor(final Object object) {
		if (object instanceof HistoryPageInput) {
			return true;
		}

		if (object instanceof IResource) {
			return typeOk((IResource) object);
		}

		if (object instanceof RepositoryTreeNode)
			return true;

		if (object instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) object)
					.getAdapter(IResource.class);
			return resource == null ? false : typeOk(resource);
		}

		return false;
	}

	private static boolean typeOk(final IResource object) {
		switch (object.getType()) {
		case IResource.FILE:
		case IResource.FOLDER:
		case IResource.PROJECT:
			return true;
		}
		return false;
	}

