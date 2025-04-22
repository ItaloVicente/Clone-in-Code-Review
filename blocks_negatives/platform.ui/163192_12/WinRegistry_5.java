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
