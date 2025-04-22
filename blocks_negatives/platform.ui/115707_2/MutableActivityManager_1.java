	/**
     * Returns whether the Java 1.4 regular expression support is available.
     * Regexp support will not be available when running against JCL Foundation (see bug 80053).
     *
	 * @return <code>true</code> if regexps are supported, <code>false</code> otherwise.
	 *
	 * @since 3.1
	 */
	private boolean isRegexpSupported() {
		try {
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

