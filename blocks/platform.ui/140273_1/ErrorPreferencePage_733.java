		Text text = new Text(parent, SWT.MULTI | SWT.READ_ONLY | SWT.WRAP);
		text.setForeground(JFaceColors.getErrorText(text.getDisplay()));
		text.setBackground(text.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		text.setText(WorkbenchMessages.ErrorPreferencePage_errorMessage);
		return text;
	}
