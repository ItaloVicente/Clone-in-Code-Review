	@Override
	public void setVisible(boolean visible) {
		if (visible)
			updateApplyButton();
		super.setVisible(visible);
	}

	@Override
	protected void updateApplyButton() {
		if (getApplyButton() != null)
			getApplyButton().setEnabled(isDirty);
	}

	@Override
	protected void performApply() {
		try {
			editor.save();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		super.performApply();
	}

	@Override
	public boolean performOk() {
		super.performOk();
		if (isDirty)
			try {
				editor.save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
		return super.performOk();
	}

	@Override
	protected void performDefaults() {
		try {
			editor.restore();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		super.performDefaults();
	}

