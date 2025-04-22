		Display display = fTree.getDisplay();
		final ViewPart view = this;
		if (display != null) {
			display.asyncExec(() -> {
				if (!fTree.isDisposed()) {
					TreeViewer viewer = fFilteredTree.getViewer();
					viewer.refresh();
					viewer.expandToLevel(2);
					fTree.setEnabled(true);
					fDeleteLogAction.setEnabled(
							fInputFile.exists() && fInputFile.equals(Platform.getLogFileLocation().toFile()));
					fOpenLogAction.setEnabled(fInputFile.exists());
					fExportLogAction.setEnabled(fInputFile.exists());
					fExportLogEntryAction.setEnabled(!viewer.getSelection().isEmpty());
					if (activate && fActivateViewAction.isChecked()) {
						IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
						if (window != null) {
							IWorkbenchPage page = window.getActivePage();
							if (page != null) {
								page.bringToTop(view);
							}
						}
					}
				}
				if (!isDisposed()) {
					fFilteredTree.getViewer().refresh();
					fFilteredTree.setEnabled(true);
				}
			});
