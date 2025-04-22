		return new ILogger() {
			@Override
			public void log(IStatus status) {
				System.err.println(status.getMessage());
				if (status.getException() != null) {
					status.getException().printStackTrace(System.err);
				}
