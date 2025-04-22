	private static IStructuredSelection getStructuredSelection(
			Collection<?> collection) {
		Object firstElement = collection.iterator().next();
		if (collection.size() == 1 && firstElement instanceof ITextSelection)
			return SelectionUtils
					.getStructuredSelection((ITextSelection) firstElement);
		else
			return new StructuredSelection(new ArrayList<Object>(collection));
	}

