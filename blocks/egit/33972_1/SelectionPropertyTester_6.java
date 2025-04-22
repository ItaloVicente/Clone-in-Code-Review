	private static IStructuredSelection getStructuredSelection(
			Collection<?> collection) {
		Object firstElement = collection.iterator().next();
		if (collection.size() == 1 && firstElement instanceof ITextSelection)
			return SelectionUtils
					.getStructuredSelection((ITextSelection) firstElement);
		else
			return new StructuredSelection(new ArrayList<Object>(collection));
	}

	private static boolean testRepositoryProperties(Repository repository,
			Object[] properties) {
		if (repository == null)
			return false;

		for (Object arg : properties) {
			String s = (String) arg;
			if (!ResourcePropertyTester.testRepositoryState(repository, s))
				return false;
		}
		return true;
	}

