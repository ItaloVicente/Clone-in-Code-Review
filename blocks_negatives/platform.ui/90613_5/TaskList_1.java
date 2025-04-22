        ISelection selection = viewer.getSelection();

        if (selection instanceof IStructuredSelection) {
			updateStatusMessage((IStructuredSelection) selection);
		} else {
			updateStatusMessage(null);
		}
