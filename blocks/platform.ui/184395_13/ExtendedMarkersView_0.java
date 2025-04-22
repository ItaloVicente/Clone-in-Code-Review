
		List<String> showInPartIds = ((WorkbenchPage) page).getShowInPartIds();
		String preferedId = "org.eclipse.jdt.ui.PackageExplorer"; //$NON-NLS-1$
		if (showInPartIds.contains(preferedId) && showIn(marker, page, preferedId)) {
			return;
		}
		for (String id : showInPartIds) {
			if (showIn(marker, page, id)) {
				return;
			}
		}
	}

	private static boolean showIn(IMarker marker, IWorkbenchPage page, String targetId) {
		try {
			ISelection selection = new StructuredSelection(marker.getResource());
			IViewPart view = page.showView(targetId);
			if (view == null) {
				return false;
			}
			ISetSelectionTarget target = Adapters.adapt(view, ISetSelectionTarget.class);
			if (target == null) {
				return false;
			}
			target.selectReveal(selection);
			((WorkbenchPage) page).performedShowIn(targetId);
			return true;
		} catch (PartInitException e) {
			MarkerSupportInternalUtilities.showViewError(e);
			return false;
		}
