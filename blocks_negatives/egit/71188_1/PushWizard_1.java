				PlatformUI.getWorkbench().getDisplay().asyncExec(
						new Runnable() {
							@Override
							public void run() {
								final Shell shell = PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getShell();
								final Dialog dialog = new PushResultDialog(
										shell, localDb, result,
										destinationString, false);
								dialog.open();
							}
						});
