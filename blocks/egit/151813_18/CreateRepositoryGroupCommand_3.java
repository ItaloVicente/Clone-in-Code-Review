		CommonViewer viewer = getView(event).getCommonViewer();
		viewer.refresh();
		viewer.setSelection(
				new StructuredSelection(new RepositoryGroupNode(group)), true);
		IStructuredSelection sel = viewer.getStructuredSelection();
		viewer.editElement(sel.getFirstElement(), 0);
