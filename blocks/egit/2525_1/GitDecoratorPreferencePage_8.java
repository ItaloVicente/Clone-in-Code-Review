		public OtherDecorationTab(TabFolder parent) {
			Composite composite = SWTUtils.createHVFillComposite(parent,
					SWTUtils.MARGINS_DEFAULT, 3);

			changeSetLabelFormat = new FormatEditor(composite,
					UIText.DecoratorPreferencesPage_changeSetLabelFormat,
					UIText.DecoratorPreferencesPage_addVariablesAction3,
					CHANGESET_LABEL_BINDINGS,
					UIPreferences.SYNC_VIEW_CHANGESET_LABEL_FORMAT);

			final TabItem tabItem = new TabItem(parent, SWT.NONE);

			Label dfLabel = SWTUtils.createLabel(composite, UIText.DecoratorPreferencesPage_dateFormat);
			dfLabel.setLayoutData(SWTUtils.createGridData(SWT.DEFAULT,
					SWT.DEFAULT, false, false));
			dateFormat = SWTUtils.createText(composite, 2);

			Label dpLabel = SWTUtils.createLabel(composite, UIText.DecoratorPreferencesPage_dateFormatPreview);
			dpLabel.setLayoutData(SWTUtils.createGridData(SWT.DEFAULT,
					SWT.DEFAULT, false, false));
			dateFormatPreview = SWTUtils.createLabel(composite, null, 2);

			tabItem.setText(UIText.DecoratorPreferencesPage_otherDecorations);
			tabItem.setControl(composite);
			tabItem.setData(UIText.DecoratorPreferencesPage_otherDecorations);

			changeSetLabelFormat.addModifyListener(this);
			dateFormat.addModifyListener(this);
		}
