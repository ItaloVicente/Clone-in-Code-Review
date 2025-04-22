			getShell().addListener(SWT.ZoomChanged, new Listener() {
				@Override
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					if (getShell().getDisplay().getPrimaryMonitor().equals(getShell().getMonitor())) {
						int dialogResponse = MessageDialog.open(MessageDialog.QUESTION, getShell(),
								WorkbenchMessages.Workbench_zoomChangedTitle,
								WorkbenchMessages.Workbench_zoomChangedMessage, SWT.NONE,
								WorkbenchMessages.Workbench_zoomChangedRestart,
								IDialogConstants.NO_LABEL);
						if (event.doit && dialogResponse == 0) {
							getWorkbenchImpl().restart(true);
						}
					}
				}
			});

