		}
	}



	protected void applyDialogFont(Composite composite) {
		Dialog.applyDialogFont(composite);
	}

	protected Label createDescriptionLabel(Composite parent) {
		Label result = null;
		String description = getDescription();
		if (description != null) {
			result = new Label(parent, SWT.WRAP);
			result.setFont(parent.getFont());
			result.setText(description);
		}
		return result;
	}

	protected Point doComputeSize() {
		if (descriptionLabel != null && body != null) {
			Point bodySize = body.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
			GridData gd = (GridData) descriptionLabel.getLayoutData();
			gd.widthHint = bodySize.x;
		}
		return getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
	}

	protected IPreferenceStore doGetPreferenceStore() {
		return null;
	}

	public IPreferencePageContainer getContainer() {
		return container;
	}

	public IPreferenceStore getPreferenceStore() {
		if (preferenceStore == null) {
