
			MenuItem openPropsView = new MenuItem(men, SWT.PUSH);
			openPropsView
					.setText(RepositoryViewUITexts.RepositoriesView_OpenPropertiesMenu);
			openPropsView.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().showView(
										IPageLayout.ID_PROP_SHEET);
					} catch (PartInitException e1) {
					}
				}

			});
		}

		if (node.getType() == RepositoryTreeNodeType.REMOTES) {

			MenuItem remoteConfig = new MenuItem(men, SWT.PUSH);
			remoteConfig
					.setText(RepositoryViewUITexts.RepositoriesView_NewRemoteMenu);
			remoteConfig.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository()))
							.open();
					scheduleRefresh();

				}

			});
		}

		if (node.getType() == RepositoryTreeNodeType.REMOTE) {

			final String name = (String) node.getObject();

			MenuItem configureUrlFetch = new MenuItem(men, SWT.PUSH);
			configureUrlFetch
					.setText(RepositoryViewUITexts.RepositoriesView_ConfigureFetchMenu);
			configureUrlFetch.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									name, false)).open();
					scheduleRefresh();

				}

			});

			MenuItem configureUrlPush = new MenuItem(men, SWT.PUSH);
			configureUrlPush
					.setText(RepositoryViewUITexts.RepositoriesView_ConfigurePushMenu);
			configureUrlPush.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									name, true)).open();
					scheduleRefresh();

				}

			});

			new MenuItem(men, SWT.SEPARATOR);

			MenuItem removeRemote = new MenuItem(men, SWT.PUSH);
			removeRemote
					.setText(RepositoryViewUITexts.RepositoriesView_RemoveRemoteMenu);
			removeRemote.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					boolean ok = MessageDialog
							.openConfirm(
									getSite().getShell(),
									RepositoryViewUITexts.RepositoriesView_ConfirmDeleteRemoteHeader,
									NLS
											.bind(
													RepositoryViewUITexts.RepositoriesView_ConfirmDeleteRemoteMessage,
													name));
					if (ok) {
						RepositoryConfig config = node.getRepository()
								.getConfig();
						config.unsetSection(REMOTE, name);
						try {
							config.save();
							scheduleRefresh();
						} catch (IOException e1) {
							MessageDialog
									.openError(
											getSite().getShell(),
											RepositoryViewUITexts.RepositoriesView_ErrorHeader,
											e1.getMessage());
						}
					}

				}

			});

			new MenuItem(men, SWT.SEPARATOR);

			MenuItem openPropsView = new MenuItem(men, SWT.PUSH);
			openPropsView
					.setText(RepositoryViewUITexts.RepositoriesView_OpenPropertiesMenu);
			openPropsView.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().showView(
										IPageLayout.ID_PROP_SHEET);
					} catch (PartInitException e1) {
					}
				}

			});
