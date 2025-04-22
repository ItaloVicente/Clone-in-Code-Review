
		ISelectionService srv = (ISelectionService) getSite().getService(
				ISelectionService.class);
		srv.addPostSelectionListener(new ISelectionListener() {

			public void selectionChanged(IWorkbenchPart part,
					ISelection selection) {

				if (linkWithSelectionAction == null
						|| !linkWithSelectionAction.isChecked())
					return;

				reactOnSelection(selection);
			}

		});
	}

	private void reactOnSelection(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection ssel = (StructuredSelection) selection;
			if (ssel.size() != 1)
				return;
			if (ssel.getFirstElement() instanceof IResource) {
				showResource((IResource) ssel.getFirstElement());
			}
			if (ssel.getFirstElement() instanceof IAdaptable) {
				IResource adapted = (IResource) ((IAdaptable) ssel
						.getFirstElement()).getAdapter(IResource.class);
				if (adapted != null)
					showResource(adapted);
			}
		}
