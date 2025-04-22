		return new ILogger() {
			@Override
			public void log(IStatus status) {
				System.err.println(status.toString());
				if( status.getException() != null ) {
					status.getException().printStackTrace(System.err);
				}
