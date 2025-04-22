	public static Throwable getCause(Throwable exception) {
		Throwable cause = null;
		if (exception != null) {
			if (exception instanceof CoreException) {
				CoreException ce = (CoreException) exception;
				cause = ce.getStatus().getException();
			} else {
				try {
					Method causeMethod = exception.getClass().getMethod("getCause"); //$NON-NLS-1$
					Object o = causeMethod.invoke(exception);
					if (o instanceof Throwable) {
						cause = (Throwable) o;
					}
				} catch (NoSuchMethodException e) {
				} catch (IllegalArgumentException e) {
