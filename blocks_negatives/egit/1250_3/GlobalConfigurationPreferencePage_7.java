		});
		addValue = new Button(valuePanel, SWT.PUSH);
		addValue.setText(UIText.GlobalConfigurationPreferencePage_AddButton);
		addValue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) tv
						.getSelection();
				Object first = sel.getFirstElement();
				if (first instanceof Entry) {
					Entry entry = (Entry) first;
					entry.addValue(valueText.getText());
					saveAndUpdate();
				}

			}
		});

		Composite buttonPanel = new Composite(main, SWT.NONE);
		buttonPanel.setLayout(new GridLayout(2, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(buttonPanel);
		final Button newEntry = new Button(buttonPanel, SWT.PUSH);
		GridDataFactory.fillDefaults().applyTo(newEntry);
		newEntry
				.setText(UIText.GlobalConfigurationPreferencePage_NewValueButton);
		newEntry.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				String suggestedKey;
				IStructuredSelection sel = (IStructuredSelection) tv
						.getSelection();
				Object first = sel.getFirstElement();
				if (first instanceof Section)
					suggestedKey = ((Section) first).name + DOT;
				else if (first instanceof SubSection) {
					SubSection sub = (SubSection) first;
					suggestedKey = sub.parent.name + DOT + sub.name + DOT;
				} else if (first instanceof Entry) {
					Entry entry = (Entry) first;
					if (entry.sectionparent != null)
						suggestedKey = entry.sectionparent.name + DOT;
					else
						suggestedKey = entry.subsectionparent.parent.name + DOT
								+ entry.subsectionparent.name + DOT;
				} else
					suggestedKey = null;

				AddConfigEntryDialog dlg = new AddConfigEntryDialog(getShell(),
						userConfig, suggestedKey);
				if (dlg.open() == Window.OK) {
					StringTokenizer st = new StringTokenizer(dlg.getKey(), DOT);
					if (st.countTokens() == 2) {
						userConfig.setString(st.nextToken(), null, st
								.nextToken(), dlg.getValue());
						saveAndUpdate();
					} else if (st.countTokens() == 3) {
						userConfig.setString(st.nextToken(), st.nextToken(), st
								.nextToken(), dlg.getValue());
						saveAndUpdate();
					} else
						Activator
								.handleError(
										UIText.GlobalConfigurationPreferencePage_WrongNumberOfTokensMessage,
										null, true);
				}
			}

		});
		remove = new Button(buttonPanel, SWT.PUSH);
		GridDataFactory.fillDefaults().applyTo(remove);
		remove.setText(UIText.GlobalConfigurationPreferencePage_RemoveAllButton);
		remove.setToolTipText(UIText.GlobalConfigurationPreferencePage_RemoveAllTooltip);
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) tv
						.getSelection();
				Object first = sel.getFirstElement();
				if (first instanceof Section) {
					Section section = (Section) first;
					if (MessageDialog
							.openConfirm(
									getShell(),
									UIText.GlobalConfigurationPreferencePage_RemoveSectionTitle,
									NLS.bind(
											UIText.GlobalConfigurationPreferencePage_RemoveSectionMessage,
											section.name))) {
						userConfig.unsetSection(section.name, null);
						saveAndUpdate();
					}
				} else if (first instanceof SubSection) {
					SubSection section = (SubSection) first;
					if (MessageDialog
							.openConfirm(
									getShell(),
									UIText.GlobalConfigurationPreferencePage_RemoveSubsectionTitle,
									NLS.bind(
											UIText.GlobalConfigurationPreferencePage_RemoveSubsectionMessage,
											section.parent.name + DOT
													+ section.name))) {
						userConfig.unsetSection(section.parent.name,
								section.name);
						saveAndUpdate();
					}
				} else {
					Activator
							.handleError(
									UIText.GlobalConfigurationPreferencePage_NoSectionSubsectionMessage,
									null, true);
				}

				super.widgetSelected(e);
			}
		});

		tv.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateEnablement();
			}
		});

		valueText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (valueText.getText().length() == 0) {
					setErrorMessage(UIText.GlobalConfigurationPreferencePage_EmptyStringNotAllowed);
					applyValue.setEnabled(false);
				} else {
					setErrorMessage(null);
					applyValue.setEnabled(true);
				}
			}
		});

		try {
			userConfig.load();
			tv.setInput(userConfig);
			location.setText(userConfig.getFile().getPath());
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (ConfigInvalidException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		tv.expandAll();
		updateEnablement();
		return main;
	}

	private void updateEnablement() {
		Object selected = ((IStructuredSelection) tv.getSelection())
				.getFirstElement();

		boolean entrySelected = selected instanceof Entry;
		boolean sectionOrSubSectionSelected = (selected instanceof Section || selected instanceof SubSection);

		if (entrySelected)
			valueText.setText(((Entry) selected).value);
		else
			valueText
					.setText(UIText.GlobalConfigurationPreferencePage_NoEntrySelectedMessage);
		applyValue.setEnabled(false);
		valueText.setEnabled(entrySelected);
		removeValue.setEnabled(entrySelected);
		addValue.setEnabled(entrySelected);
		remove.setEnabled(sectionOrSubSectionSelected);
	}

	private void saveAndUpdate() {
		try {
			userConfig.save();
			ISelection sel = tv.getSelection();
			tv.setInput(userConfig);
			tv.expandAll();
			tv.setSelection(sel, true);
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
