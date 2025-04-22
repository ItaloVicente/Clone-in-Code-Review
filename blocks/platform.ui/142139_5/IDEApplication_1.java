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
