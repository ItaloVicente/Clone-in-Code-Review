	private void processFragment(IConfigurationElement ce, List<MApplicationElement> imports,
			List<MApplicationElement> addedElements) {
		E4XMIResource applicationResource = (E4XMIResource) ((EObject) application).eResource();
		ResourceSet resourceSet = applicationResource.getResourceSet();
		IContributor contributor = ce.getContributor();
		String attrURI = ce.getAttribute("uri"); //$NON-NLS-1$
		if (attrURI == null) {
			logger.warn("Unable to find location for the model extension \"{0}\"", //$NON-NLS-1$
					contributor.getName());
			return;
		}
