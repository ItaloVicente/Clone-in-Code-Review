	private ILogListener logListener = (status, plugin) -> {
		if (status.getSeverity() == IStatus.ERROR) {
			Throwable t = status.getException();
			if (t != null) {
				e = new Exception(t);
			} else {
				e = new Exception(status.getMessage());
