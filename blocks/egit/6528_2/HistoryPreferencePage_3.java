		Composite main = getFieldEditorParent();
		GridLayoutFactory.swtDefaults().margins(0, 0).applyTo(main);
		Group showGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		showGroup.setText(UIText.HistoryPreferencePage_ShowGroupLabel);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
				.applyTo(showGroup);
