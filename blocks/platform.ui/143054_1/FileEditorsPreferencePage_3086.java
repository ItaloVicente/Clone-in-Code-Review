		this.workbench = aWorkbench;
		noDefaultAndApplyButton();
	}

	protected TableItem newResourceTableItem(IFileEditorMapping mapping, int index, boolean selected) {
		Image image = mapping.getImageDescriptor().createImage(false);
		if (image != null) {
