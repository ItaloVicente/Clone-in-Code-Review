		IContributedContentsView view = Adapters.adapt(part, IContributedContentsView.class);
		if (view != null && view.getContributingPart() == null) {
			return null;
		}

		ISelectionProvider provider = part.getSite().getSelectionProvider();
		if (provider != null) {
			IPage dPage = createFallbackPage(getPageBook());
			return new PageRec(part, dPage);
		}

