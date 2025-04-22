		IContributedContentsView view = Adapters.adapt(part, IContributedContentsView.class);
		ISelectionProvider provider = part.getSite().getSelectionProvider();
		if (provider != null && !(view != null && view.getContributingPart() == null)) {
			IPage dPage = createFallbackPage(getPageBook());
			return new PageRec(part, dPage);
		}

