			configureUrlFetch.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					WizardDialog dlg = new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									configName, false));
					if (dlg.open() == Window.OK)
						scheduleRefresh();

				}
