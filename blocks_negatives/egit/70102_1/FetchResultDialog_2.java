				PlatformUI.getWorkbench().getDisplay().asyncExec(
						new Runnable() {
							@Override
							public void run() {
								Shell shell = PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getShell();
								new FetchResultDialog(shell, repository,
										result, sourceString).open();
							}
						});
