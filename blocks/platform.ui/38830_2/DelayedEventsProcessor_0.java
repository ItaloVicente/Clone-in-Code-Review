				FileLocationDetails spec = FileLocationDetails.resolve(initialPath);
				if (spec == null || spec.fileInfo.isDirectory() || !spec.fileInfo.exists()) {
					String msg = NLS.bind(IDEWorkbenchMessages.OpenDelayedFileAction_message_fileNotFound, initialPath);
					MessageDialog.open(MessageDialog.ERROR, window.getShell(),
							IDEWorkbenchMessages.OpenDelayedFileAction_title, msg, SWT.SHEET);
				} else {
