		Composite main = getFieldEditorParent();

		Group formattingGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		formattingGroup.setText(UIText.CommitDialogPreferencePage_formatting);
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1)
				.applyTo(formattingGroup);

