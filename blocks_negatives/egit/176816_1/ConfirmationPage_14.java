			ErrorDialog
					.openError(getShell(),
							UIText.ConfirmationPage_cantConnectToAnyTitle,
							null,
							new Status(IStatus.ERROR, Activator.getPluginId(),
									message));
