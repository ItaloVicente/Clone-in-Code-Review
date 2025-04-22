		GridDataFactory.fillDefaults().grab(true, false).applyTo(deleteWorkDir);
		deleteWorkDir
				.setText(NLS
						.bind(
								UIText.DeleteRepositoryConfirmDialog_DeleteWorkingDirectoryCheckbox,
								repository.getWorkTree().getPath()));
