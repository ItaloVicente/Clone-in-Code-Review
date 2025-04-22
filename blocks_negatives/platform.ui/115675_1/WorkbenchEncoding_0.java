	/**
	 * The method for java.nio.charset.Charset.isSupported(String), or <code>null</code>
	 * if not present.  Reflection is used here to allow compilation against JCL Foundation (bug 80053).
	 */
	private static Method CharsetIsSupportedMethod = null;

	static {
		try {
			CharsetIsSupportedMethod = charsetClass.getMethod("isSupported", new Class[] { String.class }); //$NON-NLS-1$
		}
		catch (Exception e) {
		}

	}

