	/**
	 * Warning
	 *
	 * @param msg
	 * @param arg0
	 * @param arg1
	 */
	public void warn(String msg, Object arg0, Object arg1) {
		synchronized (warnings) {
			warnings.add(new Warning(MessageFormat.format(msg, arg0, arg1)));
		}
