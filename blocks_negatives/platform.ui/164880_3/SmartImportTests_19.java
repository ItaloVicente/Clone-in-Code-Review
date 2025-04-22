		ILogListener errorListener = new ILogListener() {
			@Override
			public void logging(IStatus status, String plugin) {
				if (status.getSeverity() == IStatus.ERROR) {
					errors.incrementAndGet();
				}
