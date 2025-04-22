
	/**
	 * Returns whether the given encoding is supported in the current runtime.
	 *
	 * @param encoding the encoding to test
	 * @return <code>true</code> if supported or if its support could not be determined,
	 *   <code>false</code> if not supported
	 */
	private static boolean isSupported(String encoding) {
		if (CharsetIsSupportedMethod == null) {
			return true;
		}
		try {
			Object o = CharsetIsSupportedMethod.invoke(null, new Object[] { encoding });
			return Boolean.TRUE.equals(o);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
			return false;
		}
		return true;
	}
