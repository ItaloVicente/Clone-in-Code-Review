		IStructuredSelection selection = typesListViewer.getStructuredSelection();
        boolean hasSelection = selection != null
                && selection.isEmpty() == false;

        WorkingSetDescriptor descriptor = null;
		if (hasSelection && selection != null) {
            descriptor = (WorkingSetDescriptor) selection
                    .getFirstElement();
        }
		return descriptor;
