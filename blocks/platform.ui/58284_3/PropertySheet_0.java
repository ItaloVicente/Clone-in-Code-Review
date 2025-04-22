		IWorkbenchPage page = getSite().getPage();
		if (page == null) {
			return null;
		}
		IWorkbenchPart activePart = page.getActivePart();
		if (activePart != null && activePart != this) {
			bootstrapSelection = page.getSelection();
			return activePart;
		}
		IEditorPart activeEditor = page.getActiveEditor();
		if (activeEditor != null && isImportant(activeEditor)) {
			ISelection selection = activeEditor.getSite().getSelectionProvider().getSelection();
			if ((selection instanceof IStructuredSelection) && !selection.isEmpty()) {
				bootstrapSelection = selection;
				return activeEditor;
			}
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
				IWorkbenchPart contributingPart = view.getContributingPart();
				if (contributingPart != null) {
					part = contributingPart;
				}
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
