	private boolean canBulkCreateNewBranch(Repository[] repositories) {
		for (Repository repo : repositories) {
			if (!hasBranches(repo)) {
				return false;
			}
			if (repo.isBare()) {
				return false;
			}
			if (repo.getRepositoryState() != RepositoryState.SAFE) {
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
						}) {

					@Override
					protected Control createDialogArea(Composite parent) {
						Control result = super.createDialogArea(parent);
						BranchNameNormalizer normalizer = new BranchNameNormalizer(
								getText());
						normalizer.setVisible(false);
						return result;
					}
				};
				if (dialog.open() == Window.OK) {
					String name = dialog.getValue();
					try {
						for (Repository repository : repositories) {
							Ref headRef = repository.exactRef(Constants.HEAD);
							if (headRef != null) {
								ObjectId headId = headRef.getLeaf()
										.getObjectId();
								RevCommit head = repository.parseCommit(headId);
								CreateLocalBranchOperation op = new CreateLocalBranchOperation(
										repository, name, head);
								op.execute(null);
							}
						}
						BranchOperationUI.checkout(repositories, name).start();
					} catch (Exception exception) {
						Activator.handleError(
								UIText.CreateBranchBulkDialog_Error, exception,
								true);
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
		if (StringUtils.isEmptyOrNull(newBranchName)
				|| newBranchName.trim().isEmpty()) {
			return UIText.CreateBranchPage_ChooseNameMessage;
		}
		for (Repository repo : repositories) {
			IStatus status = Utils.validateNewRefName(newBranchName, repo,
					Constants.R_HEADS, true);
			if (status.getException() != null) {
				Activator.handleStatus(status, false);
			}
			if (!status.isOK()) {
				return Activator.getDefault().getRepositoryUtil()
						.getRepositoryName(repo) + ": " + status.getMessage(); //$NON-NLS-1$
			}
		}
		return null;
	}

