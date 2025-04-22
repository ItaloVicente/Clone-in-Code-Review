	/**
	 * Paste text from the clipboard into the {@code text} field. If possible,
	 * determine the patch set number automatically. If only a change number can
	 * be determined, return {@code true} to indicate to the caller that a
	 * content assist might be helpful.
	 *
	 * @param text
	 *            {@link Text} field to paste into
	 * @return whether invoking a content assist might be helpful to the user
	 */
	private boolean doPaste(Text text) {
		Clipboard clipboard = new Clipboard(text.getDisplay());
		try {
			String clipText = (String) clipboard
					.getContents(TextTransfer.getInstance());
			if (clipText != null) {
				Change input = determineChangeFromString(
						clipText.trim());
				if (input != null) {
					String toInsert = input.getChangeNumber().toString();
					boolean replacesEverything = text.getText().trim().isEmpty()
							|| text.getSelectionText().equals(text.getText());
					boolean openContentAssist = false;
					if (input.getPatchSetNumber() != null) {
						if (replacesEverything) {
							toInsert = input.getRefName();
						} else {
							toInsert = toInsert + '/'
									+ input.getPatchSetNumber();
						}
					} else {
						openContentAssist = replacesEverything;
					}
					clipboard.setContents(new Object[] { toInsert },
							new Transfer[] { TextTransfer.getInstance() });
					try {
						text.paste();
						refTextEdited = false;
					} finally {
						clipboard.setContents(new Object[] { clipText },
								new Transfer[] { TextTransfer.getInstance() });
					}
					return openContentAssist;
				} else {
					text.paste();
				}
			}
		} finally {
			clipboard.dispose();
		}
		return false;
	}

	private void storeLastUsedUri(String uri) {
		settings.put(lastUriKey, uri.trim());
	}

	private void selectLastUsedUri() {
		String lastUri = settings.get(lastUriKey);
		if (lastUri != null) {
			int i = uriCombo.indexOf(lastUri);
			if (i != -1) {
				uriCombo.select(i);
				return;
			}
		}
		uriCombo.select(0);
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
	}

	private Collection<Change> getRefsForContentAssist()
			throws InvocationTargetException, InterruptedException {
		String uriText = uriCombo.getText();
		if (!changeRefs.containsKey(uriText)) {
			changeRefs.put(uriText, new ChangeList(repository, uriText));
		}
		ChangeList list = changeRefs.get(uriText);
		if (!list.isFinished()) {
			if (!list.mark()) {
				return null;
			}
			IWizardContainer container = getContainer();
			IRunnableWithProgress operation = monitor -> {
				monitor.beginTask(MessageFormat.format(
						UIText.AsynchronousRefProposalProvider_FetchingRemoteRefsMessage,
						uriText), IProgressMonitor.UNKNOWN);
				Collection<Change> result = list.get();
				if (monitor.isCanceled()) {
					return;
				}
				if (result == null || result.isEmpty() || fetching) {
					return;
				}
				Job showProposals = new WorkbenchJob(
						UIText.AsynchronousRefProposalProvider_ShowingProposalsJobName) {

					@Override
					public IStatus runInUIThread(IProgressMonitor uiMonitor) {
						try {
							if (container instanceof NonBlockingWizardDialog) {
								if (fetching) {
									return Status.CANCEL_STATUS;
								}
								String uriNow = uriCombo.getText();
								if (!uriNow.equals(uriText)) {
									return Status.CANCEL_STATUS;
								}
								if (refText != refText.getDisplay()
										.getFocusControl()) {
									refTextEdited = false;
									fillInPatchSet(result);
									return Status.CANCEL_STATUS;
								}
							}
							fillInPatchSet(result);
							contentProposer.openProposalPopup();
						} catch (SWTException e) {
							return Status.CANCEL_STATUS;
						} finally {
							uiMonitor.done();
						}
						return Status.OK_STATUS;
					}

				};
				showProposals.schedule();
			};
			if (container instanceof NonBlockingWizardDialog) {
				NonBlockingWizardDialog dialog = (NonBlockingWizardDialog) container;
				dialog.run(operation,
						() -> {
							if (!fetching) {
								list.cancel(ChangeList.CancelMode.ABANDON);
							}
						});
			} else {
				container.run(true, true, operation);
			}
			return null;
		}
		Collection<Change> changes = list.get();
		fillInPatchSet(changes);
		return changes;
	}

	private void fillInPatchSet(Collection<Change> changes) {
		if (refTextEdited || contentProposer.isProposalPopupOpen()) {
			return;
		}
		Change change = determineChangeFromString(refText.getText());
		if (change != null && change.getPatchSetNumber() == null) {
			Change fromGerrit = findHighestPatchSet(changes,
					change.getChangeNumber().intValue());
			if (fromGerrit != null) {
				String fullRef = fromGerrit.getRefName();
				refText.setText(fullRef);
				refTextEdited = false;
				refText.setSelection(fullRef.length());
			}
		}
