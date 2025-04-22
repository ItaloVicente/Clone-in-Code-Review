
	static ISchemeInformation getLoadingSchemeInformation(IScheme scheme, String placeholderText) {
		SchemeInformation schemeInfo = new SchemeInformation(scheme.getName(), scheme.getDescription());
		schemeInfo.setHandled(false);
		schemeInfo.setHandlerLocation(placeholderText);
		return schemeInfo;
	}
