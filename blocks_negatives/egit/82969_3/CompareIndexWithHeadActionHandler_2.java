	private static void showNoStagedFileInfo(IPath location) {
		final String title = UIText.CompareIndexWithHeadActionHandler_nothingToDoTitle;
		final String message = NLS.bind(
				UIText.CompareIndexWithHeadActionHandler_fileNotStaged,
				location.toOSString());
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog.openInformation(null, title, message);

			}
		});
	}

