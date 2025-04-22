	private String getContributionUri(MApplicationElement appElement) {
		if (appElement instanceof MPartDescriptor) {
			return ((MPartDescriptor) appElement).getContributionURI();
		} else if (appElement instanceof MAddon) {
			return ((MAddon) appElement).getContributionURI();
		} else if (appElement instanceof MHandler) {
			return ((MHandler) appElement).getContributionURI();
		}
		return null;
	}

	private boolean checkAppElementByBundleSymbolicNameAndClass(String bundleSymbolicName, String className) {
		Collection<BundleWiring> wirings = findWirings(bundleSymbolicName, bundleContext);
		if (!isBundleClassAvailable(wirings, className)) {
