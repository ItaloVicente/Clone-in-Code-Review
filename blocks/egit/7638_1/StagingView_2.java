		Section pushSection = toolkit.createSection(commitSashForm,
				ExpandableComposite.TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(pushSection);
		Composite pushArea = toolkit.createComposite(pushSection);
		pushSection.setClient(pushArea);
		toolkit.paintBordersFor(pushArea);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2)
				.applyTo(pushArea);
		pushSection.setText(UIText.CommitDialog_PushSectionTitle);
		pushCheckbox = toolkit.createButton(pushArea,
				UIText.CommitDialog_PushUpstream, SWT.CHECK);
		pushCheckbox.setSelection(getPreferenceStore().getBoolean(
					UIPreferences.COMMIT_DIALOG_PUSH_UPSTREAM));


