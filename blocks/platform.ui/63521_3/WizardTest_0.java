		Shell shell;
		ILogger oldLogger = Policy.getLog();
		try {
			Policy.setLog(new ILogger() {
				@Override
				public void log(IStatus status) {
					logged[0] = true;
				}
			});
			shell = dialog.getShell();
			dialog.close();
		} finally {
			Policy.setLog(oldLogger);
		}
