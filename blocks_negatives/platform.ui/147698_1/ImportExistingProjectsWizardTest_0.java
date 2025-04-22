	/**
	 * Copies the data to a temporary directory and returns the new location.
	 *
	 * @return the location
	 */
	private String copyDataLocation(String dataLocation) throws IOException {
		TestPlugin plugin = TestPlugin.getDefault();
		if (plugin == null) {
			throw new IllegalStateException(
					"TestPlugin default reference is null");
		}

		URL fullPathString = plugin.getBundle().getResource("/" + WS_DATA_PREFIX + "/" + dataLocation + ".zip");

		URI fileURI = null;
		try {
			fileURI = FileLocator.resolve(fullPathString).toURI();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException();
		}

		File origin = new File(fileURI);
		if (!origin.exists()) {
			throw new IllegalArgumentException();
		}

		ZipFile zFile = new ZipFile(origin);
