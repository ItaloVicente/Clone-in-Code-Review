
	static ISchemeInformation getLoadingSchemeInformation(IScheme scheme) {
		SchemeInformation schemeInfo = new SchemeInformation(scheme.getName(), scheme.getDescription());
		schemeInfo.setHandled(false);
		schemeInfo.setHandlerLocation("Loading..."); //$NON-NLS-1$
		return schemeInfo;
	}
