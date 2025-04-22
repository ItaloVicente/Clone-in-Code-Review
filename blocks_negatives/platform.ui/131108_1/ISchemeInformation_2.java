
	/**
	 * @param location
	 */
	void setHandlerLocation(String location);

	/**
	 * Returns the instance of ISchemeInformation interface.
	 *
	 * @param schemeName
	 * @param schemeDescription
	 * @param handlerLocation
	 * @return instance of ISchemeInformation
	 */
	static ISchemeInformation getInstance(String schemeName, String schemeDescription, String handlerLocation) {
		return new SchemeInformation(schemeName, schemeDescription, handlerLocation);
	}
