	/**
	 * @param path
	 * @param executable
	 * @return true if succeeded, false if not supported or failed
	 * @deprecated the implementation is highly platform dependent, consider
	 *             using {@link FS#setExecute(File, boolean)} instead
	 */
	@Deprecated
	public static boolean setExecute(File path, boolean executable) {
		if (!isFile(path))
			return false;
		return path.setExecutable(executable);
	}

