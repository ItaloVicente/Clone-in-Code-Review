		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				if (status.getException() != null)
					throw new RuntimeException(status.getException());
				fail("Unexpected status: " + status);
			}
