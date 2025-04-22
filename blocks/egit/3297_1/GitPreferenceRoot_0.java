
		Group synchronizeGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(synchronizeGroup);
		synchronizeGroup.setText(UIText.GitPreferenceRoot_SynchronizeView);
		addField(new BooleanFieldEditor(UIPreferences.SYNC_VIEW_ALWAYS_SHOW_CHANGESET_MODEL,
				UIText.GitPreferenceRoot_automaticallyEnableChangesetModel,
				synchronizeGroup));
		updateMargins(synchronizeGroup);
