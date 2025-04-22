			IContributedContentsView view = Adapters.adapt(part, IContributedContentsView.class);
			if (view != null) {
				IWorkbenchPart contributingPart = view.getContributingPart();
				if (contributingPart != null) {
					part = contributingPart;
				}
			}
