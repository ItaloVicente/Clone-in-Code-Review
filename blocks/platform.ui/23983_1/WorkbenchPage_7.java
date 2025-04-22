	public IAdaptable getInput() {
		return input;
	}

	public String getLabel() {
		String label = WorkbenchMessages.WorkbenchPage_UnknownLabel;
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) Util.getAdapter(input,
				IWorkbenchAdapter.class);
		if (adapter != null) {
