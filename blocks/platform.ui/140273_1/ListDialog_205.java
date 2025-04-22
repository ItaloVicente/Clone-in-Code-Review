		Composite parent = (Composite) super.createDialogArea(container);
		createMessageArea(parent);
		fTableViewer = new TableViewer(parent, getTableStyle());
		fTableViewer.setContentProvider(fContentProvider);
		fTableViewer.setLabelProvider(fLabelProvider);
		fTableViewer.setInput(fInput);
		fTableViewer.addDoubleClickListener(event -> {
			if (fAddCancelButton) {
