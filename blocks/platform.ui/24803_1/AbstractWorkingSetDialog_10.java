
	@Override
	protected void okPressed() {
		if (this.useDefaultWorkingSetCheckbox.getSelection()) {
			setDefaultWorkingSet((IWorkingSet) ((IStructuredSelection) this.defaultWorkingSetSelector
				.getSelection()).getFirstElement());
		}
		super.okPressed();
	}

	@Override
	public IWorkingSet getDefaultWorkingSet() {
		if (this.useDefaultWorkingSet) {
			return this.defaultWorkingSet;
		}
		return null;
	}

	@Override
	public void setDefaultWorkingSet(IWorkingSet defaultWorkingSet) {
		this.defaultWorkingSet = defaultWorkingSet;
		this.useDefaultWorkingSet = defaultWorkingSet != null;
	}
