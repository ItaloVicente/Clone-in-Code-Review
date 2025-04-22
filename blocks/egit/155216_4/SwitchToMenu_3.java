	private boolean canBulkCreateNewBranch(Repository[] repositories) {
		for (Repository repo : repositories) {
			if (!hasBranches(repo)) {
				return false;
			}
			if (RepositoryUtil.hasChanges(repo)) {
				return false;
			}
		}
		return true;
	}

	private void createBulkNewBranchMenuItem(Menu menu,
			final Repository[] repositories) {
		MenuItem newBranch = getNewBranchMenuItem(menu);
		newBranch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog dialog = new InputDialog(e.display.getActiveShell(),
						UIText.CreateBranchBulkDialog_Title,
						UIText.CreateBranchBulkDialog_Description, "", //$NON-NLS-1$
						new IInputValidator() {
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
									.findRef(Constants.HEAD);
							CreateLocalBranchOperation op = new CreateLocalBranchOperation(
									repository, name, ref,
									BranchRebaseMode.REBASE);
							op.execute(null);
						}
						BranchOperationUI.checkout(repositories, name).start();
					} catch (IOException | CoreException exception) {
						Activator.logError(UIText.CreateBranchBulkDialog_Error,
								exception);
					}
				}
			}
		});
	}

	private MenuItem getNewBranchMenuItem(Menu parentMenu) {
		MenuItem newBranch = new MenuItem(parentMenu, SWT.PUSH);
		newBranch.setText(UIText.SwitchToMenu_NewBranchMenuLabel);
		newBranch.setImage(newBranchImage);
		return newBranch;
	}

	private String validateNewBulkBranchName(String newBranchName,
			Repository[] repositories) {
		for (Repository repo : repositories) {
			IStatus status = Utils.validateNewRefName(newBranchName, repo,
					Constants.R_HEADS, true);
			if (status.getException() != null) {
				Activator.handleStatus(status, false);
			}
			if (!status.isOK()) {
				return status.getMessage();
			}
		}
		return null;
	}

