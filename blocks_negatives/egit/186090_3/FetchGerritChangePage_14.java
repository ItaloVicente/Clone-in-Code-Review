	boolean doFetch() {
		fetching = true;
		final Change change = determineChangeFromString(refText.getText());
		final String uri = uriCombo.getText();
		final ChangeList changeList = change.getPatchSetNumber() == null
				? changeRefs.remove(uri) : null;
		if (changeList != null) {
			changeList.cancel(ChangeList.CancelMode.ABANDON);
		}
		final CheckoutMode mode = getCheckoutMode();
		final boolean doCheckoutNewBranch = (mode == CheckoutMode.CREATE_BRANCH)
				&& branchCheckoutButton.getSelection();
		final boolean doActivateAdditionalRefs = showAdditionalRefs();
		final String textForTag = tagText.getText();
		final String textForBranch = branchText.getText();

		Job job = new Job(
				UIText.FetchGerritChangePage_GetChangeTaskName) {

			@Override
			public IStatus run(IProgressMonitor monitor) {
				try {
					int steps = getTotalWork(mode);
					SubMonitor progress = SubMonitor.convert(monitor,
							UIText.FetchGerritChangePage_GetChangeTaskName,
							steps + 1);
					Change finalChange = completeChange(change,
							progress.newChild(1));
					if (finalChange == null) {
						Activator.showError(NLS.bind(
								UIText.FetchGerritChangePage_NoSuchChangeMessage,
								change.getChangeNumber()), null);
						return Status.CANCEL_STATUS;
					}
					final RefSpec spec = new RefSpec()
							.setSource(finalChange.getRefName())
							.setDestination(Constants.FETCH_HEAD);
					if (progress.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					RevCommit commit = fetchChange(uri, spec,
							progress.newChild(1));
					if (mode != CheckoutMode.NOCHECKOUT && commit != null) {
						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						IWorkspaceRunnable operation = new IWorkspaceRunnable() {

							@Override
							public void run(IProgressMonitor innerMonitor)
									throws CoreException {
								SubMonitor innerProgress = SubMonitor
										.convert(innerMonitor, steps);
								switch (mode) {
								case CHECKOUT_FETCH_HEAD:
									checkout(commit.name(),
											innerProgress.newChild(1));
									break;
								case CREATE_TAG:
									assert spec != null;
									assert textForTag != null;
									createTag(spec, textForTag, commit,
											innerProgress.newChild(1));
									checkout(commit.name(),
											innerProgress.newChild(1));
									break;
								case CREATE_BRANCH:
									createBranch(textForBranch,
											doCheckoutNewBranch, commit,
											innerProgress.newChild(1));
									break;
								case CHERRY_PICK:
									cherryPick(commit,
											innerProgress.newChild(1));
									break;
								default:
									break;
								}
							}
						};
						workspace.run(operation, null, IWorkspace.AVOID_UPDATE,
								progress.newChild(steps));
					}
					if (doActivateAdditionalRefs) {
						activateAdditionalRefs();
					}
					if (mode == CheckoutMode.NOCHECKOUT) {
						repository.fireEvent(new FetchHeadChangedEvent());
					}
					storeLastUsedUri(uri);
				} catch (OperationCanceledException oe) {
					return Status.CANCEL_STATUS;
				} catch (CoreException ce) {
					return ce.getStatus();
				} catch (Exception e) {
					return Activator.createErrorStatus(e.getLocalizedMessage(),
							e);
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}

			@Override
			protected void canceling() {
				super.canceling();
				if (changeList != null) {
					changeList.cancel(ChangeList.CancelMode.INTERRUPT);
				}
			}

			private Change completeChange(Change originalChange,
					IProgressMonitor monitor)
					throws OperationCanceledException {
				if (changeList != null) {
					monitor.subTask(NLS.bind(
							UIText.AsynchronousRefProposalProvider_FetchingRemoteRefsMessage,
							uri));
					Collection<Change> changes;
					try {
						changes = changeList.get();
					} catch (InvocationTargetException
							| InterruptedException e) {
						throw new OperationCanceledException();
					}
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					Change highest = findHighestPatchSet(changes,
							originalChange.getChangeNumber().intValue());
					if (highest != null) {
						return highest;
					}
