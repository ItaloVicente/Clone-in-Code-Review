
		final Throwable[] thrownException = new Throwable[1];
		ILogListener listener = new ILogListener() {
			@Override
			public void logging(IStatus status, String plugin) {
				Throwable throwable = status.getException();
				if (throwable == null) {
					thrownException[0] = new CoreException(status);
				} else {
					thrownException[0] = throwable;
				}
			}
		};
		Platform.addLogListener(listener);
