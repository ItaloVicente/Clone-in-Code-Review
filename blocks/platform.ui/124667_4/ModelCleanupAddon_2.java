	private String getContributionUri(MApplicationElement appElement) {
		if (appElement instanceof MPartDescriptor) {
			return ((MPartDescriptor) appElement).getContributionURI();
		} else if (appElement instanceof MAddon) {
			return ((MAddon) appElement).getContributionURI();
		} else if (appElement instanceof MHandler) {
			return ((MHandler) appElement).getContributionURI();
