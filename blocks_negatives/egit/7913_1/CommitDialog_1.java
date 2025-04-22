		Section pushSection = toolkit.createSection(container,
				ExpandableComposite.TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(pushSection);
		Composite pushArea = toolkit.createComposite(pushSection);
		pushSection.setClient(pushArea);
		toolkit.paintBordersFor(pushArea);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2)
				.applyTo(pushArea);
		pushSection.setText(UIText.CommitDialog_PushSectionTitle);
		final Button pushCheckbox = toolkit.createButton(pushArea,
				UIText.CommitDialog_PushUpstream, SWT.CHECK);
		pushCheckbox.setSelection(getPreferenceStore().getBoolean(
					UIPreferences.COMMIT_DIALOG_PUSH_UPSTREAM));
		pushEnabled = getPreferenceStore().getBoolean(
				UIPreferences.COMMIT_DIALOG_PUSH_UPSTREAM);
		pushCheckbox.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				pushEnabled = pushCheckbox.getSelection();
				getPreferenceStore().setValue(
						UIPreferences.COMMIT_DIALOG_PUSH_UPSTREAM, pushEnabled);
			}
		});

