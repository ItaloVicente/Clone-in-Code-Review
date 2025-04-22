		Group infoGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(infoGroup);
		infoGroup.setText(UIText.DialogsPreferencePage_HideInfoGroupHeader);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(infoGroup);

		addField(new BooleanFieldEditor(UIPreferences.SHOW_FETCH_POPUP_SUCCESS,
				UIText.DialogsPreferencePage_HideFetchInfoDialog,
				infoGroup));

		addField(new BooleanFieldEditor(UIPreferences.SHOW_FETCH_POPUP_SUCCESS,
				UIText.DialogsPreferencePage_HidePullInfoDialog, infoGroup));

		addField(new BooleanFieldEditor(UIPreferences.SHOW_FETCH_POPUP_SUCCESS,
				UIText.DialogsPreferencePage_HidePushInfoDialog, infoGroup));

		updateMargins(infoGroup);

