		ISelection selection;
		if (selected == null) {
			selection = StructuredSelection.EMPTY;
		} else {
			selection = new StructuredSelection(selected);
		}
		boolean reveal = selection != StructuredSelection.EMPTY;
		viewer.setSelection(selection, reveal);
