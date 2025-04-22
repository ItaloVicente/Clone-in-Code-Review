				DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
				dialog.setText(IDEWorkbenchMessages.ChooseWorkspaceDialog_directoryBrowserTitle);
				dialog.setMessage(IDEWorkbenchMessages.ChooseWorkspaceDialog_directoryBrowserMessage);
				dialog.setFilterPath(getInitialBrowsePath());
				String dir = dialog.open();
				if (dir != null) {
