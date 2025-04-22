	private void processFragments(IConfigurationElement ce, boolean checkExist, List<MApplicationElement> imports,
			List<MApplicationElement> addedElements) {
		MModelFragments fragmentsContainer = getFragmentsContainer(ce);
		if (fragmentsContainer == null) {
			return;
		}
		String contributorURI = URIHelper.constructPlatformURI(ce.getContributor());
		boolean evalImports = false;
		for (MModelFragment fragment : fragmentsContainer.getFragments()) {
			Diagnostic validationResult = Diagnostician.INSTANCE.validate((EObject) fragment);
			int severity = validationResult.getSeverity();
			if (severity == Diagnostic.ERROR) {
				logger.error("Fragment from \"" + "uri.toString()" + "\" of \"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ ce.getContributor().getName() + "\" could not be validated and was not merged \"{0}\"", //$NON-NLS-1$
						fragment.toString());

				continue;
			}

			List<MApplicationElement> merged = processFragment(fragment, contributorURI, checkExist);
			if (!merged.isEmpty()) {
				addedElements.addAll(merged);
				evalImports = true;
			} else {
				logger.debug("Nothing to merge for fragment \"{0}\" of \"{1}\"", ce.getAttribute("uri"), //$NON-NLS-1$ //$NON-NLS-2$
						ce.getContributor().getName());
			}
		}
		if (evalImports) {
			imports.addAll(fragmentsContainer.getImports());
		}
	}

	private MModelFragments getFragmentsContainer(IConfigurationElement ce) {
