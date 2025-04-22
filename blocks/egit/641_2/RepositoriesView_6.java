	private void createCreateBranchItem(Menu men, final RepositoryTreeNode node) {

		final boolean remoteMode;
		final Ref ref;
		if (node.getType() == RepositoryTreeNodeType.REF) {
			remoteMode = node.getParent().getType() == RepositoryTreeNodeType.REMOTEBRANCHES;
			ref = (Ref) node.getObject();
		} else if (node.getType() == RepositoryTreeNodeType.LOCALBRANCHES) {
			remoteMode = false;
			ref = null;
		} else if (node.getType() == RepositoryTreeNodeType.REMOTEBRANCHES) {
			remoteMode = true;
			ref = null;
		} else
			return;

		MenuItem createLocal = new MenuItem(men, SWT.PUSH);
		if (remoteMode)
			createLocal.setText(UIText.RepositoriesView_NewRemoteBranchMenu);
		else
			createLocal.setText(UIText.RepositoriesView_NewLocalBranchMenu);

		createLocal.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Wizard wiz = new Wizard() {

					@Override
					public void addPages() {
						addPage(new CreateBranchPage(node.getRepository(), ref,
								remoteMode));
						setWindowTitle(UIText.RepositoriesView_NewBranchTitle);
					}

					@Override
					public boolean performFinish() {

						try {
							getContainer().run(false, true,
									new IRunnableWithProgress() {

										public void run(IProgressMonitor monitor)
												throws InvocationTargetException,
												InterruptedException {
											CreateBranchPage cp = (CreateBranchPage) getPages()[0];
											try {
												cp.createBranch(monitor);
											} catch (CoreException ce) {
												throw new InvocationTargetException(
														ce);
											} catch (IOException ioe) {
												throw new InvocationTargetException(
														ioe);
											}

										}
									});
						} catch (InvocationTargetException ite) {
							Activator
									.handleError(
											UIText.RepositoriesView_BranchCreationFailureMessage,
											ite.getCause(), true);
							return false;
						} catch (InterruptedException ie) {
						}
						return true;
					}
				};
				if (new WizardDialog(getSite().getShell(), wiz).open() == Window.OK)
					scheduleRefresh();
			}

		});

	}

	private void createDeleteBranchItem(Menu men, final RepositoryTreeNode node) {

		final Ref ref = (Ref) node.getObject();

		MenuItem deleteBranch = new MenuItem(men, SWT.PUSH);
		deleteBranch.setText(UIText.RepositoriesView_DeleteBranchMenu);

		try {
			if (node.getRepository().getFullBranch().equals(ref.getName())) {
				deleteBranch.setEnabled(false);
			}
		} catch (IOException e2) {
		}

		deleteBranch.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (!MessageDialog
						.openConfirm(
								getSite().getShell(),
								UIText.RepositoriesView_ConfirmDeleteTitle,
								NLS
										.bind(
												UIText.RepositoriesView_ConfirmBranchDeletionMessage,
												ref.getName())))
					return;

				try {
					new ProgressMonitorDialog(getSite().getShell()).run(false,
							false, new IRunnableWithProgress() {

								public void run(IProgressMonitor monitor)
										throws InvocationTargetException,
										InterruptedException {

									try {
										RefUpdate op = node.getRepository()
												.updateRef(ref.getName());
										op.setRefLogMessage("branch deleted", //$NON-NLS-1$
												false);
										op.setForceUpdate(true);
										op.delete();
										scheduleRefresh();
									} catch (IOException ioe) {
										throw new InvocationTargetException(ioe);
									}

								}
							});
				} catch (InvocationTargetException e1) {
					Activator
							.handleError(
									UIText.RepositoriesView_BranchDeletionFailureMessage,
									e1.getCause(), true);
					e1.printStackTrace();
				} catch (InterruptedException e1) {
				}
			}

		});

	}

