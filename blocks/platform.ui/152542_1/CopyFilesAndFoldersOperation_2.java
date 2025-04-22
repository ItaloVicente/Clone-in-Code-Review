			final String initial = getAutoNewNameFor(originalName, workspace).lastSegment();
			InputDialog dialog = new InputDialog(messageShell,
					IDEWorkbenchMessages.CopyFilesAndFoldersOperation_inputDialogTitle,
					NLS.bind(IDEWorkbenchMessages.CopyFilesAndFoldersOperation_inputDialogMessage, resource.getName()),
					initial, validator) {

				@Override
				protected Control createContents(Composite parent) {
					Control contents = super.createContents(parent);
					int lastIndexOfDot = initial.lastIndexOf('.');
					if (resource instanceof IFile && lastIndexOfDot > 0) {
						getText().setSelection(0, lastIndexOfDot);
