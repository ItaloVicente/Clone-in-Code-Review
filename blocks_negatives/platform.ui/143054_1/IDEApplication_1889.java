            force = true;

            try {
                if (instanceLoc.set(workspaceUrl, true)) {
                    launchData.writePersistedData();
                    writeWorkspaceVersion();

					if (parentShellVisible && isValidShell(shell)) {
						shell.setVisible(true);
						shell.forceActive();
					}
                    return null;
                }
            } catch (IllegalStateException e) {
                MessageDialog
                        .openError(
                                shell,
                                IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetTitle,
                                IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetMessage);
                return EXIT_OK;
            } catch (IOException e) {
            	  MessageDialog
                  .openError(
                          shell,
                          IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetTitle,
                          IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetMessage);
