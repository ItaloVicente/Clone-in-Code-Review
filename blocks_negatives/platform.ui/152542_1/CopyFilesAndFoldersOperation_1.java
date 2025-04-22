		messageShell.getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				ErrorDialog
						.openError(
								messageShell,
								getProblemsTitle(),
								NLS
										.bind(
												IDEWorkbenchMessages.CopyFilesAndFoldersOperation_infoNotFound,
												fileName), null);
			}
		});
