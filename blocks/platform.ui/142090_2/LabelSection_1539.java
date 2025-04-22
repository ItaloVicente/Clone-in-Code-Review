		CLabel labelLabel = getWidgetFactory()
			.createCLabel(composite, "Label:"); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(labelText,
			-ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(labelText, 0, SWT.CENTER);
		labelLabel.setLayoutData(data);
	}
