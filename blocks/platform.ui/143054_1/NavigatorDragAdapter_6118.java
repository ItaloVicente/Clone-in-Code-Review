		lastDataType = null;
		DragSource dragSource = (DragSource) event.widget;
		Control control = dragSource.getControl();
		if (control != control.getDisplay().getFocusControl()) {
			event.doit = false;
			return;
		}

		IStructuredSelection selection = (IStructuredSelection) selectionProvider.getSelection();
		for (Iterator i = selection.iterator(); i.hasNext();) {
			Object next = i.next();
			if (!(next instanceof IFile || next instanceof IFolder)) {
				event.doit = false;
				return;
			}
		}
		if (selection.isEmpty()) {
			event.doit = false;
			return;
		}
		LocalSelectionTransfer.getInstance().setSelection(selection);
		event.doit = true;
	}

	private IResource[] getSelectedResources(int resourceTypes) {
