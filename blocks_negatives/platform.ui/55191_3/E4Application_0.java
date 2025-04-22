		/*
		 * ChooseWorkspaceData launchData = new ChooseWorkspaceData(instanceLoc
		 * .getDefault());
		 *
		 * boolean force = false; while (true) { URL workspaceUrl =
		 * promptForWorkspace(shell, launchData, force); if (workspaceUrl ==
		 * null) { return false; }
		 *
		 * dialog to open to give the user a chance to correct force = true;
		 *
		 * instance data area, so other checking is unneeded if
		 * (instanceLocation.setURL(workspaceUrl, true)) {
		 * launchData.writePersistedData(); writeWorkspaceVersion(); return
		 * true; } } catch (IllegalStateException e) { MessageDialog .openError(
		 * shell, IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetTitle,
		 * IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetMessage);
		 * return false; }
		 *
		 * already in use -- force the user to choose again
		 * MessageDialog.openError(shell,
		 * IDEWorkbenchMessages.IDEApplication_workspaceInUseTitle,
		 * IDEWorkbenchMessages.IDEApplication_workspaceInUseMessage); }
		 */
