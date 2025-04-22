		if (currentSelection instanceof IStructuredSelection) {
			return (IStructuredSelection) currentSelection;
		}
		return StructuredSelection.EMPTY;
	}

	private Collection<FileDiffRegion> getSelectedFileDiffs() {
		IStructuredSelection currentSelection = getStructuredSelection();
