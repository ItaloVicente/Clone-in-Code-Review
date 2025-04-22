			getShell().addListener(SWT.ZoomChanged, event -> {
				if (getShell().getDisplay().getPrimaryMonitor().equals(getShell().getMonitor())) {
					int dialogResponse = MessageDialog.open(MessageDialog.QUESTION, getShell(),
							WorkbenchMessages.Workbench_zoomChangedTitle,
							WorkbenchMessages.Workbench_zoomChangedMessage, SWT.NONE,
							WorkbenchMessages.Workbench_zoomChangedRestart, IDialogConstants.NO_LABEL);
					if (event.doit && dialogResponse == 0) {
						getWorkbenchImpl().restart(true);
					}
				}
			});

