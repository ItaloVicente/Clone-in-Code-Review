		}
		fViewer.addDoubleClickListener(event -> {
			updateOKStatus();

			if (!(fDoubleClickSelects && fCurrStatus.isOK())) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object item = ((IStructuredSelection) selection).getFirstElement();
					if (fViewer.getExpandedState(item)) {
