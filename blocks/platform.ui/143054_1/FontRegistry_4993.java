	public FontRegistry() {
		this(Display.getCurrent(), true);
	}

	public FontRegistry(String location, ClassLoader loader)
			throws MissingResourceException {
		Display display = Display.getCurrent();
		Assert.isNotNull(display);
		readResourceBundle(location);

		cleanOnDisplayDisposal = true;
		hookDisplayDispose(display);
	}

	public FontRegistry(String location) throws MissingResourceException {
		this(location, null);
	}


	private void readResourceBundle(String location) {
		String osname = System.getProperty("os.name").trim(); //$NON-NLS-1$
		String wsname = Util.getWS();
		osname = StringConverter.removeWhiteSpaces(osname).toLowerCase();
		wsname = StringConverter.removeWhiteSpaces(wsname).toLowerCase();
		String OSLocation = location;
		String WSLocation = location;
		ResourceBundle bundle = null;
		if (osname != null) {
			OSLocation = location + "_" + osname; //$NON-NLS-1$
			if (wsname != null) {
