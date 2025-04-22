				@Override
				public void widgetSelected(SelectionEvent e) {

					WizardDialog dlg = new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									configName, true));
					if (dlg.open() == Window.OK)
						scheduleRefresh();
				}

			});

			if (pushExists) {
				MenuItem deleteFetch = new MenuItem(men, SWT.PUSH);
				deleteFetch.setText(UIText.RepositoriesView_RemovePush_menu);
				deleteFetch.addSelectionListener(new SelectionAdapter() {
