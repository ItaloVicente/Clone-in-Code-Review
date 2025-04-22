		Platform.addLogListener(new ILogListener() {

			@Override
			public void logging(IStatus status, String plugin) {
				if("org.eclipse.ui.workbench".equals(status.getPlugin())
						&& status.getSeverity() == IStatus.ERROR
						&& status.getException() instanceof IndexOutOfBoundsException) {
					errorLogged[0] = true;
				}
