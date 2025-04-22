				ErrorDialog
						.openError(
								Display.getDefault().getActiveShell(),
								"System explorer is not set", //$NON-NLS-1$
								"Please set the system explorer command in the workbench preferences.", //$NON-NLS-1$
								new Status(IStatus.ERROR, IDEWorkbenchPlugin
										.getDefault().getBundle()
										.getSymbolicName(), logMsgPrefix
										+ "Command for launching is not set.")); //$NON-NLS-1$
