		BooleanFieldEditor editor = new BooleanFieldEditor(
				UIPreferences.SHOW_FETCH_POPUP_SUCCESS,
				UIText.DialogsPreferencePage_ShowFetchInfoDialog, infoGroup);
		addField(editor);
		editor.getDescriptionControl(infoGroup)
				.setToolTipText(UIText.DialogsPreferencePage_ShowTooltip);
		editor = new BooleanFieldEditor(UIPreferences.SHOW_PUSH_POPUP_SUCCESS,
				UIText.DialogsPreferencePage_ShowPushInfoDialog, infoGroup);
		addField(editor);
		editor.getDescriptionControl(infoGroup)
				.setToolTipText(UIText.DialogsPreferencePage_ShowTooltip);

		updateMargins(infoGroup);

		Group warningsGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
