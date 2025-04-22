		IStructuredSelection selection = getSelection(context);
		if (selection != null && !selection.isEmpty()) {
			selectReveal(selection);
			return true;
		}
		return false;
	}

	private IStructuredSelection getSelection(ShowInContext context) {
