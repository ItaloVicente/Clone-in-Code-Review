
		MModelFragments fragmentsContainer = (MModelFragments) extensionRoot;
		List<MModelFragment> fragments = fragmentsContainer.getFragments();
		boolean evalImports = false;
		for (MModelFragment fragment : fragments) {
			Diagnostic validationResult = Diagnostician.INSTANCE.validate((EObject) fragment);
			int severity = validationResult.getSeverity();
			if (severity == Diagnostic.ERROR) {
						+ "\" could not be validated and will not be merged \"{0}\"", fragment); // $NON-NLS-1$ //$NON-NLS-1$
				continue;
			}
			List<MApplicationElement> elements = fragment.getElements();
			if (elements.size() == 0) {
				continue;
			}
