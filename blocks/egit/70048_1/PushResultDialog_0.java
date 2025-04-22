				Shell shell = PlatformUI.getWorkbench()
						.getModalDialogShellProvider().getShell();
				PushResultDialog dialog = new PushResultDialog(shell,
						repository, result, sourceString, modal);
				dialog.showConfigureButton(showConfigureButton);
				dialog.open();
