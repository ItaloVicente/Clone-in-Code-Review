	public void readActionExtensions(IViewPart viewPart) {
		targetPart = viewPart;
		readContributions(viewPart.getSite().getId(), TAG_CONTRIBUTION_TYPE,
				IWorkbenchRegistryConstants.PL_VIEW_ACTIONS);
		contributeToPart(targetPart);
	}
