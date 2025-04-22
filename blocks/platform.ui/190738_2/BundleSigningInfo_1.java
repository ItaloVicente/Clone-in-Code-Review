		{
			Label label = new Label(composite, SWT.NONE);
			label.setText(WorkbenchMessages.BundleSigningTray_SigningType);
			GridData data = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
			label.setLayoutData(data);

			signingType = new Label(composite, SWT.NONE);
			data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
			signingType.setData(data);
			signingType.setText(WorkbenchMessages.BundleSigningTray_Working);
		}

