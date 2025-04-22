		return new Status(IStatus.ERROR, pluginId, IStatus.OK, message, getCause(exception));
	}

	public static Throwable getCause(Throwable exception) {
		Throwable cause = null;
		if (exception != null) {
			if (exception instanceof CoreException) {
				CoreException ce = (CoreException) exception;
				cause = ce.getStatus().getException();
			} else {
				try {
					Object o = causeMethod.invoke(exception);
					if (o instanceof Throwable) {
						cause = (Throwable) o;
					}
				} catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				}
			}

			if (cause == null) {
				cause = exception;
			}
		}

		return cause;
