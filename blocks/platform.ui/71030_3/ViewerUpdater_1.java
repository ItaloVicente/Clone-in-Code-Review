
		if (selectionContains(selection, oldElement)) {
			addNewElementToSelection(oldElement, newElement, selection);
		}
	}

	private void addNewElementToSelection(final Object oldElement, final Object newElement,
			final IStructuredSelection selection) {
		final List selections = new ArrayList(selection.toList());
		selections.remove(oldElement);
		selections.add(newElement);
		viewer.setSelection(new StructuredSelection(selections));
