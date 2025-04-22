	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && refName != null)
			refText.setText(refName);
	}

	private void checkPage() {
		boolean createBranchSelected = createBranch.getSelection();
		branchText.setEnabled(createBranchSelected);
		branchText.setVisible(createBranchSelected);
		branchTextlabel.setVisible(createBranchSelected);
		branchEditButton.setVisible(createBranchSelected);
		branchCheckoutButton.setVisible(createBranchSelected);
		GridData gd = (GridData) branchText.getLayoutData();
		gd.exclude = !createBranchSelected;
		gd = (GridData) branchTextlabel.getLayoutData();
		gd.exclude = !createBranchSelected;
		gd = (GridData) branchEditButton.getLayoutData();
		gd.exclude = !createBranchSelected;
		gd = (GridData) branchCheckoutButton.getLayoutData();
		gd.exclude = !createBranchSelected;

		boolean createTagSelected = createTag.getSelection();
		tagText.setEnabled(createTagSelected);
		tagText.setVisible(createTagSelected);
		tagTextlabel.setVisible(createTagSelected);
		gd = (GridData) tagText.getLayoutData();
		gd.exclude = !createTagSelected;
		gd = (GridData) tagTextlabel.getLayoutData();
		gd.exclude = !createTagSelected;
		branchText.getParent().layout(true);

		boolean showActivateAdditionalRefs = false;
		showActivateAdditionalRefs = (checkoutFetchHead.getSelection() || updateFetchHead
				.getSelection())
				&& !Activator
						.getDefault()
						.getPreferenceStore()
						.getBoolean(
								UIPreferences.RESOURCEHISTORY_SHOW_ADDITIONAL_REFS);

		gd = (GridData) warningAdditionalRefNotActive.getLayoutData();
		gd.exclude = !showActivateAdditionalRefs;
		warningAdditionalRefNotActive.setVisible(showActivateAdditionalRefs);
		warningAdditionalRefNotActive.getParent().layout(true);

		setErrorMessage(null);
		try {
			if (refText.getText().length() > 0) {
				Change change = Change.fromRef(refText.getText());
				if (change == null) {
					change = determineChangeFromString(refText.getText());
					if (change == null) {
						setErrorMessage(
								UIText.FetchGerritChangePage_MissingChangeMessage);
						return;
					}
				}
				ChangeList list = changeRefs.get(uriCombo.getText());
				if (list != null && list.isDone()) {
					try {
						Collection<Change> changes = list.get();
						if (change.getPatchSetNumber() != null) {
							if (changes == null || !changes.contains(change)) {
								setErrorMessage(
										UIText.FetchGerritChangePage_UnknownChangeRefMessage);
								return;
							}
						} else {
							Change fromGerrit = findHighestPatchSet(changes,
									change.getChangeNumber().intValue());
							if (fromGerrit == null) {
								setErrorMessage(NLS.bind(
										UIText.FetchGerritChangePage_NoSuchChangeMessage,
										change.getChangeNumber()));
								return;
							}
						}
					} catch (InterruptedException
							| InvocationTargetException e) {
					}
				}
			} else {
				setErrorMessage(UIText.FetchGerritChangePage_MissingChangeMessage);
				return;
			}

			if (createBranchSelected) {
				setErrorMessage(branchValidator.isValid(branchText.getText()));
			} else if (createTagSelected) {
				setErrorMessage(tagValidator.isValid(tagText.getText()));
			}
		} finally {
			setPageComplete(getErrorMessage() == null);
		}
