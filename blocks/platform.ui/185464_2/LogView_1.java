	private Throttler createMutualRefresh(Display display) {
		return new Throttler(display, Duration.ofMillis(10), () -> {
			if (!fTree.isDisposed()) {
				TreeViewer viewer = fFilteredTree.getViewer();
				viewer.refresh();
				viewer.expandToLevel(2);
				fTree.setEnabled(true);
				boolean exists = fInputFile.exists();
				boolean enabled = exists && fInputFile.equals(Platform.getLogFileLocation().toFile());
				fDeleteLogAction.setEnabled(enabled);
				fOpenLogAction.setEnabled(exists);
				fExportLogAction.setEnabled(exists);
				fExportLogEntryAction.setEnabled(!viewer.getSelection().isEmpty());
			}
			if (!isDisposed()) {
				fFilteredTree.setEnabled(true);
			}
		});
	}

	private Throttler createMutualActivate(Display display) {
		return new Throttler(display, Duration.ofMillis(10), () -> {
			if (!fTree.isDisposed()) {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (window != null) {
					IWorkbenchPage page = window.getActivePage();
					if (page != null) {
						final ViewPart view = LogView.this;
						page.bringToTop(view);
					}
				}
			}
		});
	}

