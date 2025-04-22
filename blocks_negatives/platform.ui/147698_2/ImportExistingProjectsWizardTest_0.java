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
