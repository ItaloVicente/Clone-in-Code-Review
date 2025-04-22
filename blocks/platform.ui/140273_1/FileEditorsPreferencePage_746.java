	protected void fillResourceTypeTable() {
		IFileEditorMapping[] array = WorkbenchPlugin.getDefault().getEditorRegistry().getFileEditorMappings();
		for (int i = 0; i < array.length; i++) {
			FileEditorMapping mapping = (FileEditorMapping) array[i];
			mapping = (FileEditorMapping) mapping.clone(); // want a copy
			newResourceTableItem(mapping, i, false);
		}
	}

	protected Image getImage(IEditorDescriptor editor) {
		Image image = (Image) editorsToImages.get(editor);
		if (image == null) {
			image = editor.getImageDescriptor().createImage();
			editorsToImages.put(editor, image);
		}
		return image;
	}

	protected FileEditorMapping getSelectedResourceType() {
		TableItem[] items = resourceTypeTable.getSelection();
		if (items.length == 1) {
			return (FileEditorMapping) items[0].getData();
		}
		return null;
	}

	protected IEditorDescriptor[] getAssociatedEditors() {
		if (getSelectedResourceType() == null) {
