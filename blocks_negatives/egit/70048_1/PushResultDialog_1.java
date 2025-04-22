				PlatformUI.getWorkbench().getDisplay().asyncExec(
						new Runnable() {
							@Override
							public void run() {
								Shell shell = PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getShell();
								PushResultDialog dialog = new PushResultDialog(
										shell, repository, result, sourceString, modal);
								dialog.showConfigureButton(showConfigureButton);
								dialog.open();
							}
						});
