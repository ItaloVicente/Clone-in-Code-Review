	/**
	 * @return
	 */
	public static boolean supportCreatedKey() {
		if (Platform.getOS().equals(Platform.OS_WIN32) || Platform.getOS().equals(Platform.OS_MACOSX)) {
			return true;
		}
		return false;
	}

