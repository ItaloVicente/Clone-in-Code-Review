		for (String id : ((WorkbenchPage) page).getShowInPartIds()) {
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
			IShowResource target = Adapters.adapt(view, IShowResource.class);
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
