			try {
				if (instanceLoc.lock()) {
					writeWorkspaceVersion();
					return null;
				}

				File workspaceDirectory = new File(instanceLoc.getURL().getFile());
				if (workspaceDirectory.exists()) {
					if (isDevLaunchMode(applicationArguments)) {
						return EXIT_WORKSPACE_LOCKED;
					}
					MessageDialog.openError(
							shell,
							IDEWorkbenchMessages.IDEApplication_workspaceCannotLockTitle,
							NLS.bind(IDEWorkbenchMessages.IDEApplication_workspaceCannotLockMessage, workspaceDirectory.getAbsolutePath()));
				} else {
					MessageDialog.openError(
							shell,
							IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetTitle,
							IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetMessage);
				}
			} catch (IOException e) {
				IDEWorkbenchPlugin.log("Could not obtain lock for workspace location", //$NON-NLS-1$
						e);
				MessageDialog
				.openError(
						shell,
						IDEWorkbenchMessages.InternalError,
						e.getMessage());
			}
			return EXIT_OK;
		}

		ChooseWorkspaceData launchData = new ChooseWorkspaceData(instanceLoc
				.getDefault());

		boolean force = false;
