		if (node.getType() == RepositoryTreeNodeType.FETCH) {

			final String configName = (String) node.getParent().getObject();

			MenuItem configureUrlFetch = new MenuItem(men, SWT.PUSH);
			configureUrlFetch
					.setText(UIText.RepositoriesView_ConfigureFetchMenu);

			configureUrlFetch.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					WizardDialog dlg = new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									configName, false));
					if (dlg.open() == Window.OK)
						scheduleRefresh();

				}

			});

			MenuItem deleteFetch = new MenuItem(men, SWT.PUSH);
			deleteFetch.setText(UIText.RepositoriesView_RemoveFetch_menu);
			deleteFetch.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					RepositoryConfig config = node.getRepository().getConfig();
					config.unset("remote", configName, "url"); //$NON-NLS-1$ //$NON-NLS-2$
					config.unset("remote", configName, "fetch"); //$NON-NLS-1$//$NON-NLS-2$
					try {
						config.save();
						scheduleRefresh();
					} catch (IOException e1) {
						MessageDialog.openError(getSite().getShell(),
								UIText.RepositoriesView_ErrorHeader, e1
										.getMessage());
					}
				}

			});

		}

		if (node.getType() == RepositoryTreeNodeType.PUSH) {

			final String configName = (String) node.getParent().getObject();

			MenuItem configureUrlPush = new MenuItem(men, SWT.PUSH);

			configureUrlPush.setText(UIText.RepositoriesView_ConfigurePushMenu);

			configureUrlPush.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					WizardDialog dlg = new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									configName, true));
					if (dlg.open() == Window.OK)
						scheduleRefresh();
				}

			});

			MenuItem deleteFetch = new MenuItem(men, SWT.PUSH);
			deleteFetch.setText(UIText.RepositoriesView_RemovePush_menu);
			deleteFetch.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					RepositoryConfig config = node.getRepository().getConfig();
					config.unset("remote", configName, "pushurl"); //$NON-NLS-1$ //$NON-NLS-2$
					config.unset("remote", configName, "push"); //$NON-NLS-1$ //$NON-NLS-2$
					try {
						config.save();
						scheduleRefresh();
					} catch (IOException e1) {
						MessageDialog.openError(getSite().getShell(),
								UIText.RepositoriesView_ErrorHeader, e1
										.getMessage());
					}
				}

			});
		}

