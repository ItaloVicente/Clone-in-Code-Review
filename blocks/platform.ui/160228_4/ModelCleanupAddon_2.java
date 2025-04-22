	private boolean isValidHandler(MHandler handler) {
		String contributionURI = handler.getContributionURI();
		if (!URIHelper.isBundleClassUri(contributionURI)) {
			return false;
		}

			String[] bundleClass = contributionURI.substring(BUNDLECLASS_SCHEMA_LENGTH).split("/"); //$NON-NLS-1$
			String bundleSymbolicName = bundleClass[0];
			String className = bundleClass[1];
			return checkPartDescriptorByBundleSymbolicNameAndClass(bundleContext, bundleSymbolicName, className);

	}


