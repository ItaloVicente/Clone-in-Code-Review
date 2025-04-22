		listener = new ILogListener() {

			@Override
			public void logging(IStatus status, String plugin) {
				reportedErrors.add(status);
			}
		};
