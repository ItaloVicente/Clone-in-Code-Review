				ErrorDialog
						.openError(
								Display.getDefault().getActiveShell(),
								"Could not determine resource location", //$NON-NLS-1$
								"The system could not determine the selected resource's location.", //$NON-NLS-1$
								new Status(
										IStatus.ERROR,
										IDEWorkbenchPlugin.getDefault()
												.getBundle().getSymbolicName(),
										logMsgPrefix
												+ "Could not determine resource's location.")); //$NON-NLS-1$
