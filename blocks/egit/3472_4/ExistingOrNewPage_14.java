		if (externalMode)
			if (checked)
				elements = projectMoveViewer.getCheckedElements();
			else {
				ISelection selection = viewer.getSelection();
				elements = ((IStructuredSelection) selection).toArray();
			}
		else if (checked)
