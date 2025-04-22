		return new ListeningValue<IWorkbenchWindow>() {
			private final IWindowListener listener = new IWindowListener() {
				@Override
				public void windowActivated(IWorkbenchWindow window) {
					protectedSetValue(window);
				}

				@Override
				public void windowDeactivated(IWorkbenchWindow window) {
					if (window == doGetValue()) {
						protectedSetValue(null);
					}
				}

				@Override
				public void windowClosed(IWorkbenchWindow window) {
				}

				@Override
				public void windowOpened(IWorkbenchWindow window) {
				}
			};

			@Override
			protected void startListening() {
				workbench.addWindowListener(listener);
			}

			@Override
			protected void stopListening() {
				workbench.removeWindowListener(listener);
			}

			@Override
			protected IWorkbenchWindow calculate() {
				return workbench.getActiveWorkbenchWindow();
			}
		};
