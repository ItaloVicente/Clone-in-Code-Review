		focusListener = new IWindowListener() {

			private void updateUiState() {
				Display.getCurrent().asyncExec(new Runnable() {
					@Override
					public void run() {
						boolean wasActive = uiIsActive;
						uiIsActive = Display.getCurrent().getActiveShell() != null;
						if (uiIsActive != wasActive
								&& GitTraceLocation.REPOSITORYCHANGESCANNER
										.isActive())
							traceUiIsActive();
					}

					private void traceUiIsActive() {
						StringBuilder message = new StringBuilder(
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.REPOSITORYCHANGESCANNER
										.getLocation(), message.toString());
					}
				});
			}

			@Override
			public void windowOpened(IWorkbenchWindow window) {
				updateUiState();
			}

			@Override
			public void windowDeactivated(IWorkbenchWindow window) {
				updateUiState();
			}

			@Override
			public void windowClosed(IWorkbenchWindow window) {
				updateUiState();
			}

			@Override
			public void windowActivated(IWorkbenchWindow window) {
				updateUiState();
				rcs.schedule(500);
			}
		};
