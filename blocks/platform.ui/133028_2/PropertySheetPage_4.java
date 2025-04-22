		sourcePart = part;
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			viewer.setInput(((IStructuredSelection) selection).toArray());
		} else {
			IContributedContentsView view = Adapters.adapt(part, IContributedContentsView.class);
			IWorkbenchPart source = null;
			if (view != null) {
				source = view.getContributingPart();
			}
			if (source == null) {
				viewer.setInput(new Object[] { part });
			} else {
				viewer.setInput(new Object[] { source });
			}
		}
	}
