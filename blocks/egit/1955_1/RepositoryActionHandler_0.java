	private IStructuredSelection mySelection;

	public void setSelection(ISelection selection) {
		mySelection = convertSelection(null, selection);
	}

