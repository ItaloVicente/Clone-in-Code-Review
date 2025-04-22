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
	}

	private Change findHighestPatchSet(Collection<Change> changes,
			int changeNumber) {
		if (changes == null) {
