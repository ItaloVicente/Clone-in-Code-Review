
			Label dfLabel = SWTUtils.createLabel(composite, UIText.DecoratorPreferencesPage_dateFormat);
			dfLabel.setLayoutData(SWTUtils.createGridData(SWT.DEFAULT,
					SWT.DEFAULT, false, false));
			dateFormat = SWTUtils.createText(composite, 2);

			Label dpLabel = SWTUtils.createLabel(composite, UIText.DecoratorPreferencesPage_dateFormatPreview);
			dpLabel.setLayoutData(SWTUtils.createGridData(SWT.DEFAULT,
					SWT.DEFAULT, false, false));
			dateFormatPreview = SWTUtils.createLabel(composite, null, 2);

