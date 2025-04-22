			new MenuItem(men, SWT.SEPARATOR);

			MenuItem fetchItem = new MenuItem(men, SWT.PUSH);
			fetchItem.setText(UIText.RepositoriesView_FetchMenu);
			fetchItem.setImage(UIIcons.FETCH.createImage());
			fetchItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						new WizardDialog(getSite().getShell(), new FetchWizard(
								repo)).open();
					} catch (URISyntaxException e1) {
						Activator.handleError(e1.getMessage(), e1, true);
					}
				}

			});

			MenuItem pushItem = new MenuItem(men, SWT.PUSH);
			pushItem.setText(UIText.RepositoriesView_PushMenu);
			pushItem.setImage(UIIcons.PUSH.createImage());
			pushItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						new WizardDialog(getSite().getShell(), new PushWizard(
								repo)).open();
					} catch (URISyntaxException e1) {
						Activator.handleError(e1.getMessage(), e1, true);
					}
				}

			});

