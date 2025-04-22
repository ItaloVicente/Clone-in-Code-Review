
	void setHandlerInstanceLocation(String location);

	static ISchemeInformation getInstance(String schemeName, String schemeDescription, String handlerLocation) {
		return new SchemeInformation(schemeName, schemeDescription, handlerLocation);
	}
