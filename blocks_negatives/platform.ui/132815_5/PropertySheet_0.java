		IContributedContentsView view = Adapters.adapt(part, IContributedContentsView.class);
        IWorkbenchPart source = null;
        if (view != null) {
			source = view.getContributingPart();
		}
        if (source != null) {
			super.partActivated(source);
		} else {
			super.partActivated(part);
		}
