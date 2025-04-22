				try {
					workspaceUrl = promptForWorkspace(shell, launchData, force);
				} catch (OperationCanceledException e) {
					launchData = new ChooseWorkspaceData(instanceLoc.getDefault());
					continue;
				}
