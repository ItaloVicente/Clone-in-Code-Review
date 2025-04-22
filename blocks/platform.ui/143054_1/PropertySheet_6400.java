
		IContributedContentsView view = Adapters.adapt(part, IContributedContentsView.class);
		IWorkbenchPart source = null;
		if (view != null) {
			source = view.getContributingPart();
		}
		if (source == null) {
			source = part;
		}

