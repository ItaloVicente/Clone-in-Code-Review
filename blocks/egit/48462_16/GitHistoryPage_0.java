	private void attachSelectionTracker() {
		if (selectionTracker == null) {
			selectionTracker = new ISelectionListener() {
				@SuppressWarnings("restriction")
				@Override
				public void selectionChanged(IWorkbenchPart part,
						ISelection selection) {
					if (part instanceof GenericHistoryView
							|| !(selection instanceof IStructuredSelection)) {
						return;
					}
					lastSelection = (IStructuredSelection) selection;
				}
			};
			getSite().getPage().addSelectionListener(selectionTracker);
		}
	}

	private void detachSelectionTracker() {
		if (selectionTracker != null) {
			getSite().getPage().removeSelectionListener(selectionTracker);
		}
	}

