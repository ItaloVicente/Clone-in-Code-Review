		} else {
			IContributedContentsView view = Adapters.adapt(part, IContributedContentsView.class);
			IWorkbenchPart source = null;
			if (view != null) {
				source = view.getContributingPart();
			}
			viewer.setInput(new Object[] { source != null ? source : part });
