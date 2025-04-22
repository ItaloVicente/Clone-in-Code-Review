    public static Throwable getCause(Throwable exception) {
        Throwable cause = null;
        if (exception != null) {
            if (exception instanceof CoreException) {
                CoreException ce = (CoreException)exception;
                cause = ce.getStatus().getException();
            } else {
            	try {
            		Method causeMethod = exception.getClass().getMethod("getCause", new Class[0]); //$NON-NLS-1$
            		Object o = causeMethod.invoke(exception, new Object[0]);
            		if (o instanceof Throwable) {
            			cause = (Throwable) o;
            		}
            	}
            	catch (NoSuchMethodException e) {
            	} catch (IllegalArgumentException e) {
