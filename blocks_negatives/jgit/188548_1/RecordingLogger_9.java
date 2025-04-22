	/**
	 * Warning
	 *
	 * @param msg
	 *            warning message
	 */
	public void warn(String msg) {
		synchronized (warnings) {
			warnings.add(new Warning(msg));
		}
