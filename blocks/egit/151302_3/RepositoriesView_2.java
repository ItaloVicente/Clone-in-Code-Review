		viewer.addPostSelectionChangedListener(event -> {
			if (!((Boolean) reactOnSelection.getValue()).booleanValue()) {
				return;
			}
			ISelection selection = event.getSelection();
			if (selection.isEmpty()
					|| !(selection instanceof IStructuredSelection)) {
				return;
			}
			IStructuredSelection sel = (IStructuredSelection) selection;
			if (sel.size() > 1) {
				return;
			}
			Object selected = sel.getFirstElement();
			if (selected instanceof FileNode) {
				showEditor((FileNode) selected);
			}
		});
