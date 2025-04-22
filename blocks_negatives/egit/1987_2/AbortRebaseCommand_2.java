					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									Shell shell = PlatformUI.getWorkbench()
											.getActiveWorkbenchWindow()
											.getShell();
									new RebaseResultDialog(shell, repository,
											rebase.getResult()).open();
								}
							});
						}
					});
