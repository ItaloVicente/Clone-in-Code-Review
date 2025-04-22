	private void createBulkNewBranchMenuItem(Menu menu,
			final Repository[] repositories) {
		MenuItem newBranch = new MenuItem(menu, SWT.PUSH);
		newBranch.setText(UIText.SwitchToMenu_NewBranchMenuLabel);
		newBranch.setImage(newBranchImage);
		newBranch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog dialog = new InputDialog(e.display.getActiveShell(),
						"Create Branch (Bulk)", //$NON-NLS-1$
						"Create branches from the current head of all selected repositories. Enter the new branch name:", //$NON-NLS-1$
						"", new IInputValidator() { //$NON-NLS-1$
							@Override
							public String isValid(String newBranchName) {
								return validateNewBulkBranchName(newBranchName,
										repositories);
							}
						});
				if (dialog.open() == Window.OK) {
					String name = dialog.getValue();
					try {
						for (Repository repository : repositories) {
							Ref ref = repository.getRefDatabase()
									.findRef("HEAD");//$NON-NLS-1$
							CreateLocalBranchOperation op = new CreateLocalBranchOperation(
									repository, name, ref,
									BranchRebaseMode.REBASE);
							op.execute(null);
						}
						BranchOperationUI.checkout(repositories, name).start();
					} catch (IOException | CoreException exception) {
						Activator.logError("error during bulk branch creation", //$NON-NLS-1$
								exception);
					}
				}
			}
		});
	}

	private String validateNewBulkBranchName(String newBranchName,
			Repository[] repositories) {
		if (newBranchName.isEmpty()) {
			return "Branch name must not be empty."; //$NON-NLS-1$
		}
		for (Repository repository : repositories) {
			try {
				if (repository.getRefDatabase()
						.findRef(newBranchName) != null) {
					return "Branch already exists for at least one selected repository"; //$NON-NLS-1$
				}
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
		}
		return null;
	}

