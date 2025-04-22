			} catch (IOException e) {
				IDEWorkbenchPlugin.log("Could not obtain lock for workspace location", //$NON-NLS-1$
						e);
				MessageDialog
				.openError(
						shell,
						IDEWorkbenchMessages.InternalError,
						e.getMessage());
