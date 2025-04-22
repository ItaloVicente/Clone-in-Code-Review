				FileLocationDetails details = FileLocationDetails.resolve(initialPath);
				if (details == null || details.fileInfo.isDirectory() || !details.fileInfo.exists()) {
					String msg = NLS.bind(IDEWorkbenchMessages.OpenDelayedFileAction_message_fileNotFound, initialPath);
					MessageDialog.open(MessageDialog.ERROR, window.getShell(),
							IDEWorkbenchMessages.OpenDelayedFileAction_title, msg, SWT.SHEET);
				} else {
