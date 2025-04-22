
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		newButton = new Button(parent, SWT.PUSH);
		newButton.setFont(JFaceResources.getDialogFont());
		newButton.setText(UIText.CheckoutDialog_NewBranch);
		setButtonLayoutData(newButton);
		((GridLayout) parent.getLayout()).numColumns++;

		renameButton = new Button(parent, SWT.PUSH);
		renameButton.setFont(JFaceResources.getDialogFont());
		renameButton.setText(UIText.CheckoutDialog_Rename);
		setButtonLayoutData(renameButton);
		((GridLayout) parent.getLayout()).numColumns++;

		deleteteButton = new Button(parent, SWT.PUSH);
		deleteteButton.setFont(JFaceResources.getDialogFont());
		deleteteButton.setText(UIText.CheckoutDialog_Delete);
		setButtonLayoutData(deleteteButton);
		((GridLayout) parent.getLayout()).numColumns++;

		renameButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				String refName = refNameFromDialog();
				String refPrefix;

				if (refName.startsWith(Constants.R_HEADS))
					refPrefix = Constants.R_HEADS;
				else if (refName.startsWith(Constants.R_REMOTES))
					refPrefix = Constants.R_REMOTES;
				else if (refName.startsWith(Constants.R_TAGS))
					refPrefix = Constants.R_TAGS;
				else {
					return;
				}

				String branchName = refName.substring(refPrefix.length());

				InputDialog labelDialog = getRefNameInputDialog(NLS.bind(
						UIText.CheckoutDialog_QuestionNewBranchNameMessage,
						branchName, refPrefix), refPrefix, branchName);
				if (labelDialog.open() == Window.OK) {
					String newRefName = refPrefix + labelDialog.getValue();
					try {
						new Git(repo).branchRename().setOldName(refName)
								.setNewName(labelDialog.getValue()).call();
						branchTree.refresh();
						markRef(newRefName);
					} catch (Throwable e1) {
						reportError(e1,
								UIText.CheckoutDialog_ErrorCouldNotRenameRef,
								refName, newRefName, e1.getMessage());
					}
				}
			}
		});
		newButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CreateBranchWizard wiz = new CreateBranchWizard(repo,
						refNameFromDialog());
				if (new WizardDialog(getShell(), wiz).open() == Window.OK) {
					String newRefName = wiz.getNewBranchName();
					try {
						branchTree.refresh();
						markRef(Constants.R_HEADS + newRefName);
						if (repo.getBranch().equals(newRefName))
							CheckoutDialog.this.okPressed();
					} catch (Throwable e1) {
						reportError(
								e1,
								UIText.CheckoutDialog_ErrorCouldNotCreateNewRef,
								newRefName);
					}
				}
			}
		});

		deleteteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent selectionEvent) {
				try {
					CommonUtils
							.runCommand(
									"org.eclipse.egit.ui.RepositoriesViewDeleteBranch", //$NON-NLS-1$
									(IStructuredSelection) branchTree.getSelection());
				} catch (Throwable e) {
					reportError(e,
							UIText.CheckoutDialog_ErrorCouldNotDeleteRef,
							refNameFromDialog());
				}
			}
		});

		super.createButtonsForButtonBar(parent);
	}

	private InputDialog getRefNameInputDialog(String prompt,
			final String refPrefix, String initialValue) {
		InputDialog labelDialog = new InputDialog(getShell(),
				UIText.CheckoutDialog_QuestionNewBranchTitle, prompt,
				initialValue, ValidationUtils.getRefNameInputValidator(repo,
						refPrefix, true));
		labelDialog.setBlockOnOpen(true);
		return labelDialog;
	}

	private void reportError(Throwable e, String message, Object... args) {
		String msg = NLS.bind(message, args);
		Activator.handleError(msg, e, true);
	}

