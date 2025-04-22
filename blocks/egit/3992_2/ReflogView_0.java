		ISelection selection = service.getSelection();
		if (selection != null && !selection.isEmpty()) {
			IWorkbenchPart part = site.getPage().getActivePart();
			if (part != null)
				selectionChangedListener.selectionChanged(part, selection);
		}

		site.setSelectionProvider(refLogTableTreeViewer);

