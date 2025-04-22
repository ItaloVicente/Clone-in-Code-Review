		{
			Label label = new Label(composite, SWT.NONE);
			label.setText(WorkbenchMessages.BundleSigningTray_Signing_Certificate);
			GridData data = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
			data.horizontalSpan = 2;
			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = 2;
			certificate = new StyledText(composite, SWT.READ_ONLY | SWT.MULTI | SWT.WRAP);
			certificate.setText(WorkbenchMessages.BundleSigningTray_Working);
			certificate.setLayoutData(data);
		}
