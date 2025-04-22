		Ref ref;
		ISelection currentSelection = getCurrentSelectionChecked(event);
		if (currentSelection instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) currentSelection;
			Object selected = selection.getFirstElement();
			ref = getRef(selected);
		} else
			ref = null;
