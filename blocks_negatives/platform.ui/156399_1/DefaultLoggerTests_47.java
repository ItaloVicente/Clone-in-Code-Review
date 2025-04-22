		Platform.addLogListener(new ILogListener() {
			@Override
			public void logging(IStatus status, String plugin) {
				if (plugin.equals(RUNTIME_ID)) {
					loggedStatus = status;
				}
