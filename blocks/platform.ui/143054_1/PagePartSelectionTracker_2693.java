		String id = part.getSite().getId();
		if (part instanceof IViewPart) {
			String secondaryId = ((IViewPart) part).getViewSite().getSecondaryId();
			if (secondaryId != null) {
				id = id + ':' + secondaryId;
			}
