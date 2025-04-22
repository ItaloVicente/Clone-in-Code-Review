	private void setWarningText(String warning) {
		GridData gd = (GridData) warningComposite.getLayoutData();
		gd.exclude = warning == null;
		warningComposite.setVisible(!gd.exclude);
		if (warning != null)
			warningText.setText(warning);
		else
			warningText.setText(""); //$NON-NLS-1$
		warningComposite.getParent().layout(true);
	}

