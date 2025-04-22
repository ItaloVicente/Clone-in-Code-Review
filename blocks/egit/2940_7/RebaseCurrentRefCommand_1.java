		ISelection currentSelection = getCurrentSelectionChecked(event);
		if (currentSelection instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) currentSelection;
			Object selected = selection.getFirstElement();

			ref = getRef(selected);
		} else
			ref = null;

		final Repository repository = getRepository(event);

		if (ref == null) {
