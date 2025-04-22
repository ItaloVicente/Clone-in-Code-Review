
	private Repository getRepository(Collection<?> collection) {
		if (collection.isEmpty()) {
			return null;
		}
		IStructuredSelection selection = null;
		Object first = collection.iterator().next();
		if (collection.size() == 1 && first instanceof ITextSelection) {
			selection = SelectionUtils
					.getStructuredSelection((ITextSelection) first);
		} else {
			selection = new StructuredSelection(new ArrayList<>(collection));
		}
		return SelectionUtils.getRepository(selection);

	}
