	public static void log(String message, Throwable t) {
		IStatus status = StatusUtil.newStatus(IStatus.ERROR, message, t);
		log(message, status);
	}

	public static void log(Class clazz, String methodName, Throwable t) {
		String msg = MessageFormat.format("Exception in {0}.{1}: {2}", //$NON-NLS-1$
