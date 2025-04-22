		IWorkbenchPage page = getSite().getPage();
		if (page == null) {
			return null;
		}
		IWorkbenchPart activePart = page.getActivePart();
		if (activePart != null && activePart != this) {
			bootstrapSelection = page.getSelection();
			return activePart;
		}
		IViewReference[] viewrefs = page.getViewReferences();
		IWorkbenchPart interesting = null;
		for (IViewReference ref : viewrefs) {
			IWorkbenchPart part = ref.getPart(false);
			if (part == null || part == this || !page.isPartVisible(part)) {
				continue;
			}
			IContributedContentsView view = Adapters.adapt(part, IContributedContentsView.class);
			if (view != null) {
				part = view.getContributingPart();
			}
			if (!isImportant(part)) {
				continue;
			}
			ISelection selection = part.getSite().getSelectionProvider().getSelection();
			if (!(selection instanceof IStructuredSelection) || selection.isEmpty()) {
				continue;
			}
			if (interesting != null) {
				return null;
			}
			interesting = part;
		}
		if (interesting != null) {
			bootstrapSelection = interesting.getSite().getSelectionProvider().getSelection();
		}
		return interesting;
