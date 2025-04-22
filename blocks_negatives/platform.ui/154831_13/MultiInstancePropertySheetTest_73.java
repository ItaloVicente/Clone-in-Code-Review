	private ILogListener logListener = new ILogListener() {
		@Override
		public void logging(IStatus status, String plugin) {
			if (status.getSeverity() == IStatus.ERROR) {
				Throwable t = status.getException();
				if (t != null) {
					e = new Exception(t);
				} else {
					e = new Exception(status.getMessage());
				}
