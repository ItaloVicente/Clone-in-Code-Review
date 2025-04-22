	/**
	 * @return umask returned from running umask command in a shell
	 * @since 4.0
	 */
	protected static String readUmask() {
		Process p;
