		String originalCompatibilityViewClass = partDescriptor.getPersistedState()
				.get(ViewRegistry.ORIGINAL_COMPATIBILITY_VIEW_CLASS);
		if (COMPATIBILITY_VIEW_URI.equals(contributionURI) && originalCompatibilityViewClass != null) {
			String originalCompatibilityViewBundle = partDescriptor.getPersistedState()
					.get(ViewRegistry.ORIGINAL_COMPATIBILITY_VIEW_BUNDLE);
			return checkPartDescriptorByBundleSymbolicNameAndClass(bundle, originalCompatibilityViewBundle,
					originalCompatibilityViewClass);
		} else if (!COMPATIBILITY_EDITOR_URI.equals(contributionURI)) {
			String[] bundleClass = contributionURI.substring(14).split("/"); //$NON-NLS-1$
			String bundleSymbolicName = bundleClass[0];
			String className = bundleClass[1];
			return checkPartDescriptorByBundleSymbolicNameAndClass(bundle, bundleSymbolicName, className);
		}

		return true;
	}

	private boolean checkPartDescriptorByBundleSymbolicNameAndClass(Bundle bundle, String bundleSymbolicName,
			String className) {
