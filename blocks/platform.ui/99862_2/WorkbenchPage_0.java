		IViewSite viewSite = part.getViewSite();
		if (viewSite == null)
			return null;
		String compoundId = viewSite.getId();
		String secondaryId = viewSite.getSecondaryId();
		if (secondaryId != null && !secondaryId.isEmpty())
			compoundId += ":" + secondaryId; //$NON-NLS-1$
		MPart mpart = partService.findPart(compoundId);
