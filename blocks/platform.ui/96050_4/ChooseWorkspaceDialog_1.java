	private void createSettingsControls(Composite workArea) {
		final FormToolkit toolkit = new FormToolkit(workArea.getDisplay());
		workArea.addDisposeListener(e -> toolkit.dispose());
		final ScrolledForm form = toolkit.createScrolledForm(workArea);
		form.getBody().setBackground(workArea.getBackground());
		form.getBody().setLayout(new GridLayout());
		form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final ExpandableComposite copySettingsExpandable = toolkit.createExpandableComposite(form.getBody(),
				ExpandableComposite.TWISTIE);

		copySettingsExpandable.setText(IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_SettingsGroupName);
		copySettingsExpandable.setBackground(workArea.getBackground());
		copySettingsExpandable.setLayout(new GridLayout());
		copySettingsExpandable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		copySettingsExpandable.addExpansionListener(new IExpansionListener() {

			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(true);
				Point size = getInitialSize();
				Shell shell = getShell();
				shell.setBounds(getConstrainedShellBounds(
						new Rectangle(shell.getLocation().x, shell.getLocation().y, size.x, size.y)));
			}

			@Override
			public void expansionStateChanging(ExpansionEvent e) {

			}
		});

		Composite sectionClient = toolkit.createComposite(copySettingsExpandable);
		sectionClient.setBackground(workArea.getBackground());
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.marginLeft = 14;
		layout.spacing = 6;
		sectionClient.setLayout(layout);

		createPromptForCopyPreferences(sectionClient);

		if (createButtons(toolkit, sectionClient)) {
			copySettingsExpandable.setExpanded(true);
		}

		copySettingsExpandable.setClient(sectionClient);
	}

	private boolean createButtons(FormToolkit toolkit,Composite sectionClient) {
		IDialogSettings dialogSettings = getDialogSettings();
		String[] enabledSettings = null;
		if (dialogSettings != null) {
			enabledSettings = getDialogSettings().getArray(ENABLED_TRANSFERS);
		}

		for (final IConfigurationElement settingsTransfer : SettingsTransfer.getSettingsTransfers()) {
			final Button button = toolkit.createButton(sectionClient, settingsTransfer.getAttribute(ATT_NAME),
					SWT.CHECK);

			ControlDecoration deco = new ControlDecoration(button, SWT.TOP | SWT.RIGHT);
			Image image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_WARNING)
					.getImage();
			deco.setDescriptionText(IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_copySettingsDecoLabel);
			deco.setImage(image);

			toggleDecoForSettingsImportButtons(button, deco);
			getCombo().addModifyListener(e -> toggleDecoForSettingsImportButtons(button, deco));

			String helpId = settingsTransfer.getAttribute(ATT_HELP_CONTEXT);

			if (helpId != null && PlatformUI.isWorkbenchRunning()) {
				PlatformUI.getWorkbench().getHelpSystem().setHelp(button, helpId);
			}

			if (enabledSettings != null && enabledSettings.length > 0) {

				String id = settingsTransfer.getAttribute(ATT_ID);
				for (String enabledSetting : enabledSettings) {
					if (enabledSetting.equals(id)) {
						button.setSelection(true);
						selectedSettings.add(settingsTransfer);
						break;
					}
				}
			}

			button.setBackground(sectionClient.getBackground());
			button.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (button.getSelection()) {
						selectedSettings.add(settingsTransfer);
					} else {
						selectedSettings.remove(settingsTransfer);
					}
					toggleDecoForSettingsImportButtons(button, deco);
					settingsWorkspaceLink.setEnabled(!selectedSettings.isEmpty());
				}
			});

		}
		return enabledSettings != null && enabledSettings.length > 0;
	}

	private void toggleDecoForSettingsImportButtons(Button button, ControlDecoration deco) {
		if (!button.getSelection()) {
			deco.hide();
			return;
		}

		if (Files.exists(Paths.get(getWorkspaceLocation()), LinkOption.NOFOLLOW_LINKS)) {
			deco.show();
		} else {
			deco.hide();
		}
	}

	private void createPromptForCopyPreferences(Composite parent) {
		settingsWorkspaceLink = new Link(parent, SWT.WRAP);
		settingsWorkspaceLink.setEnabled(!selectedSettings.isEmpty());
		settingsWorkspaceLink.setText("<a>" + "No workspace selected" + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		settingsWorkspaceLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ImportPreferencesDialog copyPreferencesDialog = new ImportPreferencesDialog(getShell(), launchData);
				copyPreferencesDialog.open();
			}
		});
	}
