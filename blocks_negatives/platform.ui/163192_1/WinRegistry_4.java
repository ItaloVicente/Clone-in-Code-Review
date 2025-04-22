			IPath keyPath = new Path(key);
			String parent = keyPath.removeLastSegments(1).toOSString();
			String child = keyPath.lastSegment();
			Object parentHandle = METHOD_openKey.invoke(USER_ROOT, toByteArray(parent), KEY_DELETE, KEY_DELETE);
			int result = (Integer) METHOD_WinRegDeleteKey.invoke(null, parentHandle, toByteArray(child));
			METHOD_closeKey.invoke(USER_ROOT, parentHandle);
			if (result != 0) {
			}
		} catch (Exception e) {
			throw new WinRegistryException(e.getMessage(), e);
		}
	}

	private static byte[] toByteArray(String str) {
		if (str == null) {
			return new byte[] { 0 };
		}
		try {
			return (byte[]) METHOD_stringToByteArray.invoke(null, str);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * Converts a null-terminated byte array to java string
	 *
	 * @throws UnsupportedEncodingException
	 */
	private static String byteArrayToString(byte[] array) throws UnsupportedEncodingException {
		byte[] truncatedArray = new byte[array.length - 1];
		System.arraycopy(array, 0, truncatedArray, 0, truncatedArray.length);
		String result;
		try {
			result = new String(truncatedArray, "Windows-1252"); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			result = new String(truncatedArray, "ISO-8859-1"); //$NON-NLS-1$
		}
		return result;
	}

	private static String toString(byte[] bytes) {
		try {
			return byteArrayToString(bytes);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
