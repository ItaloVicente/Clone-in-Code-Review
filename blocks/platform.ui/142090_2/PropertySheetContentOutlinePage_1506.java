		super.createControl(parent);
		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new WorkbenchContentProvider());
		viewer.setLabelProvider(new WorkbenchLabelProvider());
		viewer.setInput(this.model);
		viewer.expandAll();
